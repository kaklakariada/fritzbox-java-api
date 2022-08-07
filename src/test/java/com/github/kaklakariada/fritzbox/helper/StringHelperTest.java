/**
 * A Java API for managing FritzBox HomeAutomation
 * Copyright (C) 2017 Christoph Pirkl <christoph at users.sourceforge.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General  License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General  License for more details.
 *
 * You should have received a copy of the GNU General  License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.kaklakariada.fritzbox.helper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringHelperTest {

    @Test
    void isIntegerNumberTest() {
        assertFalse(StringHelper.isIntegerNumber(null), "Test null");
        assertFalse(StringHelper.isIntegerNumber(""), "Test empty");
        assertFalse(StringHelper.isIntegerNumber(" "), "Test blank");
        assertTrue(StringHelper.isIntegerNumber(" 1 "), "Test numeric enclosed by blank");
        assertFalse(StringHelper.isIntegerNumber("1 1"), "Test numeric enclosing blank");
        assertTrue(StringHelper.isIntegerNumber("\u0967\u0968\u0969"), "Test Unicode");
        assertTrue(StringHelper.isIntegerNumber("1"), "Test Integer");
        assertFalse(StringHelper.isIntegerNumber("1.1"), "Test Double");
        assertFalse(StringHelper.isIntegerNumber("1.1D"), "Test Double");
    }

    @Test
    void isNumericTest() {
        assertFalse(StringHelper.isNumeric(null), "Test null");
        assertFalse(StringHelper.isNumeric(""), "Test empty");
        assertFalse(StringHelper.isNumeric(""), "Test blank");
        assertFalse(StringHelper.isNumeric(" 1 "), "Test numeric blank");
        assertTrue(StringHelper.isNumeric("\u0967\u0968\u0969"), "Test Unicode");
        assertTrue(StringHelper.isNumeric("1"), "Test empty");
        assertFalse(StringHelper.isNumeric("1.1"), "Test Double");
        assertFalse(StringHelper.isNumeric("1.1D"), "Test Double");
    }
}
