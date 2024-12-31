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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * PBKDF2 challenge-response. See <a href=
 * "https://avm.de/fileadmin/user_upload/Global/Service/Schnittstellen/AVM_Technical_Note_-_Session_ID_english_2021-05-03.pdf">...</a>
 */
class Pbkdf2ChallengeResponse implements ChallengeResponse {

    @Override
    public String calculateResponse(final String challenge, final String password) {
        final String[] challengeParts = challenge.split("\\$");
        if (challengeParts.length != 5) {
            throw new IllegalArgumentException("Challenge '" + challenge + "' has an invalid format");
        }
        final int iter1 = Integer.parseInt(challengeParts[1]);
        final byte[] salt1 = fromHex(challengeParts[2]);
        final int iter2 = Integer.parseInt(challengeParts[3]);
        final byte[] salt2 = fromHex(challengeParts[4]);
        final byte[] hash1 = pbkdf2HmacSha256(password.getBytes(StandardCharsets.UTF_8), salt1, iter1);
        final byte[] hash2 = pbkdf2HmacSha256(hash1, salt2, iter2);
        return challengeParts[4] + "$" + toHex(hash2);
    }

    /** Hex string to bytes */
    static byte[] fromHex(final String hexString) {
        final int len = hexString.length() / 2;
        final byte[] ret = new byte[len];
        for (int i = 0; i < len; i++) {
            ret[i] = (byte) Short.parseShort(hexString.substring(i * 2, i *
                    2 + 2), 16);
        }
        return ret;
    }

    /** Byte array to hex string */
    static String toHex(final byte[] bytes) {
        final StringBuilder s = new StringBuilder(bytes.length * 2);
        for (final byte b : bytes) {
            s.append(String.format("%02x", b));
        }
        return s.toString();
    }

    /**
     * Create a pbkdf2 HMAC by applying the Hmac iter times as specified. We can't use the Android-internal PBKDF2 here,
     * as it only accepts char[] arrays, not bytes (for multi-stage hashing)
     */
    static byte[] pbkdf2HmacSha256(final byte[] password, final byte[] salt, final int iters) {
        final String algorithm = "HmacSHA256";
        try {
            final Mac sha256mac = Mac.getInstance(algorithm);
            sha256mac.init(new SecretKeySpec(password, algorithm));
            final byte[] ret = new byte[sha256mac.getMacLength()];
            byte[] tmp = new byte[salt.length + 4];
            System.arraycopy(salt, 0, tmp, 0, salt.length);
            tmp[salt.length + 3] = 1;
            for (int i = 0; i < iters; i++) {
                tmp = sha256mac.doFinal(tmp);
                for (int k = 0; k < ret.length; k++) {
                    ret[k] ^= tmp[k];
                }
            }
            return ret;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("Failed to calculate HMAC", e);
        }
    }
}
