/**
 * A Java API for managing FritzBox HomeAutomation
 * Copyright (C) 2017 Christoph Pirkl <christoph at users.sourceforge.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.kaklakariada.fritzbox.http;

import java.io.IOException;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.fritzbox.FritzBoxException;
import com.github.kaklakariada.fritzbox.mapping.Deserializer;

import okhttp3.CertificatePinner;
import okhttp3.HttpUrl;
import okhttp3.HttpUrl.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * This class allows executing http requests against a server. Responses are converted using the given
 * {@link Deserializer}.
 */
public class HttpTemplate {
    private static final Logger LOG = LoggerFactory.getLogger(HttpTemplate.class);

    private final OkHttpClient httpClient;
    private final HttpUrl baseUrl;
    private final Deserializer deserializer;

    public static HttpTemplate create(final String baseUrl, final String certificatChecksum) {
        final HttpUrl url = HttpUrl.parse(baseUrl);
        return new HttpTemplate(createUnsafeOkHttpClient(url, certificatChecksum), new Deserializer(), url);
    }

    HttpTemplate(final OkHttpClient httpClient, final Deserializer deserializer, final HttpUrl baseUrl) {
        this.httpClient = httpClient;
        this.deserializer = deserializer;
        this.baseUrl = baseUrl;
    }

    private static OkHttpClient createUnsafeOkHttpClient(final HttpUrl url, final String certificatChecksum) {
        final okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(TrustSelfSignedCertificates.getUnsafeSslSocketFactory(), new NullTrustManager());
        // builder.hostnameVerifier(new NullHostnameVerifier());
        if (certificatChecksum != null) {
            LOG.info("Pin certificate for host {} to checksum {}", url.host(), certificatChecksum);
            final CertificatePinner certificatePinner = new CertificatePinner.Builder()
                    .add(url.host(), certificatChecksum)
                    .build();
            builder.certificatePinner(certificatePinner);
        }
        return builder.build();
    }

    public <T> T get(final String path, final Class<T> resultType) {
        return get(path, QueryParameters.builder().build(), resultType);
    }

    public <T> T get(final String path, final QueryParameters parameters, final Class<T> resultType) {
        final HttpUrl url = createUrl(path, parameters);
        return get(resultType, url);
    }

    public <T> T post(final String path, final QueryParameters parameters, final Class<T> resultType) {
        final HttpUrl url = createUrl(path, parameters);
        return post(resultType, url);
    }

    private <T> T get(final Class<T> resultType, final HttpUrl url) {
        final Request request = new Request.Builder().url(url).get().build();
        final Response response = execute(request);
        return parse(response, resultType);
    }

    private <T> T post(final Class<T> resultType, final HttpUrl url) {
        final MediaType mediaType = MediaType.parse("application/xml");
        final RequestBody emptyBody = RequestBody.create(new byte[0], mediaType);
        final Request request = new Request.Builder().url(url).post(emptyBody).build();
        final Response response = execute(request);
        return parse(response, resultType);
    }

    private <T> T parse(final Response response, final Class<T> resultType) {
        if (!response.isSuccessful()) {
            throw new FritzBoxException("Request failed: " + response);
        }
        final String body = getBodyAsString(response);
        if (body.startsWith("HTTP/1.0 500 Internal Server Error")) {
            throw new FritzBoxException("Request failed: " + body);
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace("Got response {} with body\n'{}'", response, body.trim());
        }
        return deserializer.parse(body.trim(), resultType);
    }

    private String getBodyAsString(final Response response) {
        try {
            return response.body().string();
        } catch (final IOException e) {
            throw new HttpException("Error getting body from response " + response, e);
        }
    }

    private HttpUrl createUrl(final String path, final QueryParameters parameters) {
        final Builder builder = baseUrl.newBuilder().encodedPath(path);
        for (final Entry<String, String> param : parameters.getParameters().entrySet()) {
            builder.addQueryParameter(param.getKey(), param.getValue());
        }
        return builder.build();
    }

    private Response execute(final Request request) {
        LOG.trace("Executing request {}", request);
        try {
            final Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                if (response.code() == 403) {
                    throw new AccessForbiddenException(
                            "Authentication failed, session id outdated or invalid: " + response);
                }
                throw new HttpException("Request failed with response " + response);
            }
            return response;
        } catch (final IOException e) {
            throw new HttpException("Error executing requst " + request, e);
        }
    }
}
