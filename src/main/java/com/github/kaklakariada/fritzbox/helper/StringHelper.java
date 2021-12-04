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
package com.github.kaklakariada.fritzbox.helper;

public class StringHelper {
    
    private StringHelper(){
        // Not instantiable
    }
    
    /**
     * <p>Note that the method does not allow for a leading sign, either positive or negative.</p>
     * 
     * <pre>
     * StringUtils.isIntegerNumber(null)  = false
     * StringHelper.isIntegerNumber(""))  = false
     * StringHelper.isIntegerNumber(" ")  = false
     * StringHelper.isIntegerNumber(" 1 ") = true
     * StringHelper.isIntegerNumber("123")  = true
     * StringUtils.isIntegerNumber("\u0967\u0968\u0969")  = true
     * StringHelper.isIntegerNumber("1.1") = false
     * StringHelper.isIntegerNumber("1.1D") = false
     * </pre>
     * 
     * 
     * @param cs  the String to check, may be null
     * @return {@code true} if only contains digits or is enclosed by blanks, and is non-null
     */
    public static boolean isIntegerNumber(final String cs) {
        if (isEmpty(cs) || !isNumeric(cs.trim())) {
            return false;
        }
        try {
            Integer.parseInt(cs.trim());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    /**
     * <h4>Code copied 'as is' from apache-commons-lang3, class StringUtils.isNumeric()</h4>
     * 
     * <p>Checks if the CharSequence contains only Unicode digits.
     * A decimal point is not a Unicode digit and returns false.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code false}.</p>
     *
     * <p>Note that the method does not allow for a leading sign, either positive or negative.
     * Also, if a String passes the numeric test, it may still generate a NumberFormatException
     * when parsed by Integer.parseInt or Long.parseLong, e.g. if the value is outside the range
     * for int or long respectively.</p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = false
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * StringUtils.isNumeric("-123") = false
     * StringUtils.isNumeric("+123") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if only contains digits, and is non-null
     */
    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * <h4>Code copied 'as is' from apache-commons-lang3, class StringUtils.isEmpty()</h4>
     * 
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
