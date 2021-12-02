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
    public void isIntegerNumberTest() {
        assertEquals("Test null", false, statistics.isIntegerNumber(null));
        assertEquals("Test Integer", true, statistics.isIntegerNumber("1"));
        assertEquals("Test Double", false, statistics.isIntegerNumber("1.1"));
        assertEquals("Test Double", false, statistics.isIntegerNumber("1.1D"));
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
        assertEquals("Number of entries", 0, result.size());
        
        statistics.setCsvValues("");
        result = statistics.getValues();
        assertEquals("Number of entries", 1, result.size());
        assertEquals("First number", Optional.empty(), result.get(0));
        
        statistics.setCsvValues("1111, 22222");
        result = statistics.getValues();
        assertEquals("Number of entries", 2, result.size());
        assertEquals("First number", Double.valueOf(11.11), result.get(0).get());
    }
}
