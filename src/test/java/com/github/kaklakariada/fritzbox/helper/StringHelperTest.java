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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringHelperTest {

    @Test
    public void isIntegerNumberTest() {
        assertFalse("Test null", StringHelper.isIntegerNumber(null));
        assertFalse("Test empty", StringHelper.isIntegerNumber(""));
        assertFalse("Test blank", StringHelper.isIntegerNumber(" "));
        assertTrue("Test numeric enclosed by blank", StringHelper.isIntegerNumber(" 1 "));
        assertFalse("Test numeric enclosing blank", StringHelper.isIntegerNumber("1 1"));
        assertTrue("Test Unicode", StringHelper.isIntegerNumber("\u0967\u0968\u0969"));
        assertTrue("Test Integer", StringHelper.isIntegerNumber("1"));
        assertFalse("Test Double", StringHelper.isIntegerNumber("1.1"));
        assertFalse("Test Double", StringHelper.isIntegerNumber("1.1D"));
    }

    @Test
    public void isNumericTest() {
        assertFalse("Test null", StringHelper.isNumeric(null));
        assertFalse("Test empty", StringHelper.isNumeric(""));
        assertFalse("Test blank", StringHelper.isNumeric(""));
        assertFalse("Test numeric blank", StringHelper.isNumeric(" 1 "));
        assertTrue("Test Unicode", StringHelper.isNumeric("\u0967\u0968\u0969"));
        assertTrue("Test empty", StringHelper.isNumeric("1"));
        assertFalse("Test Double", StringHelper.isNumeric("1.1"));
        assertFalse("Test Double", StringHelper.isNumeric("1.1D"));
    }

}
