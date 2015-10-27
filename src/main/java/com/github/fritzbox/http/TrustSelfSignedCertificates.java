package com.github.fritzbox.http;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrustSelfSignedCertificates {
    private final static Logger LOG = LoggerFactory.getLogger(TrustSelfSignedCertificates.class);

    public static void trustSelfSignedSSL() {
        final SSLContext ctx = getSSLContext("TLS");
        final X509TrustManager tm = new NullTrustManager();
        initContext(ctx, tm);
        LOG.warn("Using {} as default for SSL context {}", tm, ctx);
        SSLContext.setDefault(ctx);
    }

    private static void initContext(final SSLContext ctx, final X509TrustManager tm) {
        try {
            ctx.init(null, new TrustManager[] { tm }, null);
        } catch (final KeyManagementException e) {
            throw new RuntimeException("Error initializing ssl context", e);
        }
    }

    private static SSLContext getSSLContext(String algorithm) {
        try {
            return SSLContext.getInstance(algorithm);
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException("Algorithm " + algorithm + " not found", e);
        }
    }

    private static final class NullTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(final X509Certificate[] xcs, final String string) throws CertificateException {
            LOG.debug("Check client trusted {}", string);
        }

        @Override
        public void checkServerTrusted(final X509Certificate[] xcs, final String string) throws CertificateException {
            LOG.debug("Check server trusted {}", string);
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            LOG.debug("Get accepted issuers");
            return null;
        }
    }
}
