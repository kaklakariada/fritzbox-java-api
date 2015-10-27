package com.github.fritzbox.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NullHostnameVerifier implements HostnameVerifier {
    private final static Logger LOG = LoggerFactory.getLogger(NullHostnameVerifier.class);

    @Override
    public boolean verify(String hostname, SSLSession session) {
        LOG.warn("Ignore ssl certificate for {}: {}", hostname, session);
        return true;
    }
}
