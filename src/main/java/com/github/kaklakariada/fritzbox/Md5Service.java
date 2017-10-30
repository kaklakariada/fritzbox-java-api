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
package com.github.kaklakariada.fritzbox;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Helper class to calculate Md5 hashes
 *
 */
public class Md5Service {

  public static final Charset CHARSET_UTF_16LE = Charset.forName("utf-16le");

  /**
   * create the md5 for the given string
   * 
   * @param s
   *          - the string input
   * @return - the md5
   */
  public String md5(final String s) {
    final MessageDigest digest = getMd5MessageDigest();
    final byte[] binary = digest.digest(s.getBytes(CHARSET_UTF_16LE));
    return buildHexString(binary);
  }

  /**
   * convert the given data to a hex string
   * 
   * @param data
   *          - input
   * @return - the hex string
   */
  public static String buildHexString(final byte[] data) {
    final StringBuilder hexString = new StringBuilder();
    for (final byte b : data) {
      String h = Integer.toHexString(0xFF & b);
      while (h.length() < 2) {
        h = "0" + h;
      }
      hexString.append(h);
    }
    return hexString.toString();
  }

  /**
   * the the Md5 Message Digest
   * 
   * @return - an MD5 MessageDigest instance
   */
  private MessageDigest getMd5MessageDigest() {
    try {
      return MessageDigest.getInstance("MD5");
    } catch (final NoSuchAlgorithmException e) {
      throw new AssertionError("Error getting MD5 message digest", e);
    }
  }
}
