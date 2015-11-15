package com.github.kaklakariada.fritzbox;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Service {

    private static final Charset CHARSET_UTF_16LE = Charset.forName("utf-16le");

    public String md5(final String s) {
        final MessageDigest digest = getMd5MessageDigest();
        final byte[] binary = digest.digest(s.getBytes(CHARSET_UTF_16LE));
        return buildHexString(binary);
    }

    private String buildHexString(final byte[] data) {
        final StringBuilder hexString = new StringBuilder();
        for (final byte aMessageDigest : data) {
            String h = Integer.toHexString(0xFF & aMessageDigest);
            while (h.length() < 2) {
                h = "0" + h;
            }
            hexString.append(h);
        }
        return hexString.toString();
    }

    private MessageDigest getMd5MessageDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (final NoSuchAlgorithmException e) {
            throw new AssertionError("Error getting MD5 message digest", e);
        }
    }
}
