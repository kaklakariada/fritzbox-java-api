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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringHelperTest {
    
    @Test
    public void isIntegerNumberTest() {
        assertEquals("Test null", false, StringHelper.isIntegerNumber(null));
        assertEquals("Test empty", false, StringHelper.isIntegerNumber(""));
        assertEquals("Test blank", false, StringHelper.isIntegerNumber(" "));
        assertEquals("Test numeric enclosed by blank", true, StringHelper.isIntegerNumber(" 1 "));
        assertEquals("Test numeric enclosing blank", false, StringHelper.isIntegerNumber("1 1"));
        assertEquals("Test Unicode", true, StringHelper.isIntegerNumber("\u0967\u0968\u0969"));
        assertEquals("Test Integer", true, StringHelper.isIntegerNumber("1"));
        assertEquals("Test Double", false, StringHelper.isIntegerNumber("1.1"));
        assertEquals("Test Double", false, StringHelper.isIntegerNumber("1.1D"));
    }

    @Test
    public void isNumericTest() {
        assertEquals("Test null", false, StringHelper.isNumeric(null));
        assertEquals("Test empty", true, StringHelper.isNumeric(""));
        assertEquals("Test blank", true, StringHelper.isNumeric(""));
        assertEquals("Test numeric blank", true, StringHelper.isNumeric(" 1 "));
        assertEquals("Test Unicode", true, StringHelper.isNumeric("\u0967\u0968\u0969"));
        assertEquals("Test empty", true, StringHelper.isNumeric("1"));
        assertEquals("Test Double", false, StringHelper.isNumeric("1.1"));
        assertEquals("Test Double", false, StringHelper.isNumeric("1.1D"));
    }

}
