package com.github.kaklakariada.fritzbox.http;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class creates an {@link SSLSocketFactory} that trusts all certificates.
 * 
 * @see #getUnsafeSslSocketFactory()
 */
public class TrustSelfSignedCertificates {

    private final static Logger LOG = LoggerFactory.getLogger(TrustSelfSignedCertificates.class);

    public static SSLSocketFactory getUnsafeSslSocketFactory() {
        final SSLContext ctx = getSSLContext("TLS");
        final X509TrustManager tm = new NullTrustManager();
        initContext(ctx, tm);
        return ctx.getSocketFactory();
    }

    private static void initContext(final SSLContext ctx, final X509TrustManager tm) {
        try {
            ctx.init(null, new TrustManager[] { tm }, new SecureRandom());
        } catch (final KeyManagementException e) {
            throw new HttpException("Error initializing ssl context", e);
        }
    }

    private static SSLContext getSSLContext(String algorithm) {
        try {
            return SSLContext.getInstance(algorithm);
        } catch (final NoSuchAlgorithmException e) {
            throw new HttpException("Algorithm " + algorithm + " not found", e);
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
