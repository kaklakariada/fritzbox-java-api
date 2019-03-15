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

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class NullTrustManager implements X509TrustManager {
    private static final Logger LOG = LoggerFactory.getLogger(NullTrustManager.class);

    // Don't throw exception, we want to accept any certificate
    @SuppressWarnings("squid:S4424")
    @Override
    public void checkClientTrusted(final X509Certificate[] xcs, final String authType) {
        LOG.trace("Check client trusted auth type '{}'", authType);
    }

    // Don't throw exception, we want to accept any certificate
    @SuppressWarnings("squid:S4424")
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