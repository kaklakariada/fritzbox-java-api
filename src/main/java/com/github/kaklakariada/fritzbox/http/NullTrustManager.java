package com.github.kaklakariada.fritzbox.http;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class NullTrustManager implements X509TrustManager {
    private final static Logger LOG = LoggerFactory.getLogger(NullTrustManager.class);

    @Override
    public void checkClientTrusted(final X509Certificate[] xcs, final String authType) {
        LOG.trace("Check client trusted auth type '{}'", authType);
    }

    @Override
    public void checkServerTrusted(final X509Certificate[] xcs, final String authType) {
        LOG.trace("Check server trusted auth type '{}'", authType);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        LOG.trace("Get accepted issuers");
        return new X509Certificate[0];
    }
}