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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import com.github.kaklakariada.fritzbox.helper.StringHelper;

@Root(name = "stats")
public class Statistics {

    private MeasurementUnit measurementUnit;

    @Attribute(name = "count", required = false)
    private int count;

    @Attribute(name = "grid", required = false)
    private int grid;

    @Text()
    private String csvValues;

    public int getCount() {
        return count;
    }

    public int getGrid() {
        return grid;
    }

    /**
     * Provide the gathered data as provided by Fritz!Box
     * @return
     */
    public String getCsvValues() {
        return csvValues;
    }
    
    /**
     * Just for unit test provided. 
     * Therefore it is set to protected
     * @return
     */
    protected void setCsvValues(String csvValues) {
        this.csvValues = csvValues;
    }

    /**
     * Provide the gathered data as computed as meant to be used by AVM
     * @return
     */
    public List<Optional<Number>> getValues() {
        if (getCsvValues() == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(getCsvValues().split(","))
                .stream()
                .map(aValue -> Optional.ofNullable(computeValue(aValue)))
                .collect(Collectors.toList());
    }

    /**
     * Provide the measurement unit to be used with the statistics.<p>
     * Consists of:
     * <ul>
     * <li>measurment unit [V, W, Wh, %]</li>
     * <li>precision as double to multiply with the gathered Integer</li>
     * </ul>
     * Sample: The Voltage is measured in 'V' (Volt) and has a precision of '0.001'. The number 237123 provided
     * by the statistics must be multiplied by the precision which gives us 237.123 V.
     * 
     * @return
     */
    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(final MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    protected Number computeValue(String aValue) {
        Number numberValue = null;
        if (StringHelper.isIntegerNumber(aValue)) {
            Integer intValue =  Integer.valueOf(aValue.trim());
            if (measurementUnit.getPrescision() instanceof Double) {
                numberValue = Double.valueOf(intValue*(Double)measurementUnit.getPrescision());
            } else {
                numberValue = Integer.valueOf(intValue  * (Integer)measurementUnit.getPrescision());
            }
            return numberValue;
        } 
        return null;
    }
}