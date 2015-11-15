package com.github.fritzbox.http;

import java.io.IOException;
import java.util.Map.Entry;

import org.simpleframework.xml.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.HttpUrl.Builder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class HttpTemplate {
    private final static Logger LOG = LoggerFactory.getLogger(HttpTemplate.class);

    private final OkHttpClient httpClient;
    private final HttpUrl baseUrl;

    private final Serializer serializer;

    public HttpTemplate(OkHttpClient httpClient, Serializer serializer, String urlScheme, String host, int port) {
        this.httpClient = httpClient;
        this.serializer = serializer;
        this.baseUrl = new HttpUrl.Builder().scheme(urlScheme).host(host).port(port).build();
    }

    public <T> T get(String path, Class<T> resultType) {
        return get(path, QueryParameters.builder().build(), resultType);
    }

    public <T> T get(String path, QueryParameters parameters, Class<T> resultType) {
        final HttpUrl url = createUrl(path, parameters);
        return get(resultType, url);
    }

    public <T> T post(String path, QueryParameters parameters, Class<T> resultType) {
        final HttpUrl url = createUrl(path, parameters);
        return post(resultType, url);
    }

    private <T> T get(Class<T> resultType, HttpUrl url) {
        final Request request = new Request.Builder().url(url).get().build();
        final Response response = execute(request);
        return parse(response, resultType);
    }

    private <T> T post(Class<T> resultType, HttpUrl url) {
        final MediaType mediaType = MediaType.parse("application/xml");
        final RequestBody emptyBody = RequestBody.create(mediaType, new byte[0]);
        final Request request = new Request.Builder().url(url).post(emptyBody).build();
        final Response response = execute(request);
        return parse(response, resultType);
    }

    private <T> T parse(final Response response, Class<T> resultType) {
        String body = null;
        try {
            body = response.body().string();
            LOG.trace("Got response {} with body\n'{}'", response, body.trim());
            final T object = serializer.read(resultType, body);
            LOG.trace("Parsed response: {}", object);
            return object;
        } catch (final Exception e) {
            throw new RuntimeException("Error parsing response " + response + " with body\n'" + body + "'", e);
        }
    }

    private HttpUrl createUrl(String path, QueryParameters parameters) {
        final Builder builder = baseUrl.newBuilder().encodedPath(path);
        for (final Entry<String, String> param : parameters.getParameters().entrySet()) {
            builder.addQueryParameter(param.getKey(), param.getValue());
        }
        return builder.build();
    }

    private Response execute(Request request) {
        LOG.trace("Executing request {}", request);
        try {
            return httpClient.newCall(request).execute();
        } catch (final IOException e) {
            throw new RuntimeException("Error executing requst " + request, e);
        }
    }
}
