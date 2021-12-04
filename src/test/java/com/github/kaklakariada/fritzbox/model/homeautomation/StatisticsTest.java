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

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;

public class StatisticsTest {
    
    private static Statistics  statistics;
    
    @BeforeClass
    public static void setupStatistics() {
        statistics = new Statistics();
        statistics.setMeasurementUnit(MeasurementUnit.POWER);
    }
    
    @Test
    public void computeValueTest() {
        assertEquals("Test null", null, statistics.computeValue(null));
        assertEquals("Test Integer", Double.valueOf(0.02), statistics.computeValue("2"));
        assertEquals("Test Double", null, statistics.computeValue("1.1"));
        assertEquals("Test Double", null, statistics.computeValue("1.1D"));
    }
    
    @Test
    public void getCsvValuesTest() {
        statistics.setCsvValues(null);
        List<Optional<Number>> result = statistics.getValues();
        assertEquals("(1) Number of entries", 0, result.size());
        
        statistics.setCsvValues("");
        result = statistics.getValues();
        assertEquals("(2) Number of entries", 1, result.size());
        assertEquals("(2) First number", Optional.empty(), result.get(0));
        
        statistics.setCsvValues("1111, 22222");
        result = statistics.getValues();
        assertEquals("(3) Number of entries", 2, result.size());
        assertEquals("(3) First number", Double.valueOf(11.11), result.get(0).get());
        
        statistics.setCsvValues("1111,,22222,-");
        result = statistics.getValues();
        assertEquals("(4) Number of entries", 4, result.size());
        assertEquals("(4) First number", Double.valueOf(11.11), result.get(0).get());
        assertEquals("(4) Second number is empty", true, result.get(1).isEmpty());
        assertEquals("(4) Third number", Double.valueOf(222.22), result.get(2).get());
        assertEquals("(4) Fourth number is empty", true, result.get(3).isEmpty());
        
        statistics.setCsvValues(",1111,-");
        result = statistics.getValues();
        assertEquals("(5) Number of entries", 3, result.size());
        assertEquals("(5) First number is empty", true, result.get(0).isEmpty());
        assertEquals("(5) Second number", Double.valueOf(11.11), result.get(1).get());
        assertEquals("(5) Third number is empty", true, result.get(2).isEmpty());
    }
}
