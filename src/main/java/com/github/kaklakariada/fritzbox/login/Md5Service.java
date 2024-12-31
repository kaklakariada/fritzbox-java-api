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
package com.github.kaklakariada.fritzbox.login;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Md5Service {

    String md5(final String s) {
        final MessageDigest digest = getMd5MessageDigest();
        final byte[] binary = digest.digest(s.getBytes(StandardCharsets.UTF_16LE));
        return buildHexString(binary);
    }

    // Concatenating strings in a loop is ok here
    @SuppressWarnings("squid:S1643")
    private String buildHexString(final byte[] data) {
        final StringBuilder hexString = new StringBuilder();
        for (final byte aMessageDigest : data) {
            final StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & aMessageDigest));
            while (h.length() < 2) {
                h.insert(0, "0");
            }
            hexString.append(h);
        }
        return hexString.toString();
    }

    @SuppressWarnings("java:S4790") // MD5 still required as fallback
    private MessageDigest getMd5MessageDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (final NoSuchAlgorithmException e) {
            throw new AssertionError("Error getting MD5 message digest", e);
        }
    }
}
