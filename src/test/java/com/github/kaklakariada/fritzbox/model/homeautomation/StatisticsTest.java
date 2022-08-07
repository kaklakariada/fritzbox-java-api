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
package com.github.kaklakariada.fritzbox.model.homeautomation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StatisticsTest {

    private static Statistics statistics;

    @BeforeAll
    static void setupStatistics() {
        statistics = new Statistics();
        statistics.setMeasurementUnit(MeasurementUnit.POWER);
    }

    @Test
    void computeValueTest() {
        assertEquals(null, statistics.computeValue(null), "Test null");
        assertEquals(Double.valueOf(0.02), statistics.computeValue("2"), "Test Integer");
        assertEquals(null, statistics.computeValue("1.1"), "Test Double");
        assertEquals(null, statistics.computeValue("1.1D"), "Test Double");
    }

    @Test
    void getCsvValuesTest() {
        statistics.setCsvValues(null);
        List<Optional<Number>> result = statistics.getValues();
        assertEquals(0, result.size(), "(1) Number of entries");

        statistics.setCsvValues("");
        result = statistics.getValues();
        assertEquals(1, result.size(), "(2) Number of entries");
        assertEquals(Optional.empty(), result.get(0), "(2) First number");

        statistics.setCsvValues("1111, 22222");
        result = statistics.getValues();
        assertEquals(2, result.size(), "(3) Number of entries");
        assertEquals(Double.valueOf(11.11), result.get(0).get(), "(3) First number");

        statistics.setCsvValues("1111,,22222,-");
        result = statistics.getValues();
        assertEquals(4, result.size(), "(4) Number of entries");
        assertEquals(Double.valueOf(11.11), result.get(0).get(), "(4) First number");
        assertEquals(true, result.get(1).isEmpty(), "(4) Second number is empty");
        assertEquals(Double.valueOf(222.22), result.get(2).get(), "(4) Third number");
        assertEquals(true, result.get(3).isEmpty(), "(4) Fourth number is empty");

        statistics.setCsvValues(",1111,-");
        result = statistics.getValues();
        assertEquals(3, result.size(), "(5) Number of entries");
        assertEquals(true, result.get(0).isEmpty(), "(5) First number is empty");
        assertEquals(Double.valueOf(11.11), result.get(1).get(), "(5) Second number");
        assertEquals(true, result.get(2).isEmpty(), "(5) Third number is empty");
    }
}
