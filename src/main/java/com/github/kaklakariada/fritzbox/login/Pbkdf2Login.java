package com.github.kaklakariada.fritzbox.login;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * PBKDF2 challenge-response. See
 * https://avm.de/fileadmin/user_upload/Global/Service/Schnittstellen/AVM_Technical_Note_-_Session_ID_english_2021-05-03.pdf
 */
class Pbkdf2Login implements ChallengeResponse {

    public String calculateResponse(final String challenge, final String password) {
        final String[] challengeParts = challenge.split("\\$");
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
     * Create a pbkdf2 HMAC by appling the Hmac iter times as specified. We can't use the Android-internal PBKDF2 here,
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