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

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
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
        final SSLContext sslContext = getSSLContext("TLS");
        initializeSslContext(sslContext);
        return sslContext.getSocketFactory();
    }

    private static void initializeSslContext(SSLContext sslContext) {
        final KeyManager[] keyManagers = null;
        final TrustManager[] trustManagers = new TrustManager[] { new NullTrustManager() };
        final SecureRandom secureRandom = new SecureRandom();
        final SSLContext sslContext1 = sslContext;
        try {
            sslContext1.init(keyManagers, trustManagers, secureRandom);
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
            return null;
        }
    }
}
