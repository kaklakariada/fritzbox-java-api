package com.github.fritzbox.http;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class CustomHostnameVerifierHttpRequestFactory extends SimpleClientHttpRequestFactory {
    private final static Logger LOG = LoggerFactory.getLogger(CustomHostnameVerifierHttpRequestFactory.class);

    private final HostnameVerifier verifier;

    public CustomHostnameVerifierHttpRequestFactory(final HostnameVerifier verifier) {
        this.verifier = verifier;
    }

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (connection instanceof HttpsURLConnection) {
            LOG.trace("Setting hostname verifier {} for url connection {}", verifier, connection.getURL());
            ((HttpsURLConnection) connection).setHostnameVerifier(verifier);
        }
        super.prepareConnection(connection, httpMethod);
    }
}
