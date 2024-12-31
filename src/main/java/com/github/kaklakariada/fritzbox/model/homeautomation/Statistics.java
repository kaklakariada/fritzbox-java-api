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

import java.time.Instant;
import java.util.*;

import org.simpleframework.xml.*;

import com.github.kaklakariada.fritzbox.helper.StringHelper;

@Root(name = "stats")
public class Statistics {

    private MeasurementUnit measurementUnit;

    @Attribute(name = "count", required = false)
    private int count;

    @Attribute(name = "grid", required = false)
    private int grid;

    @Attribute(name = "datatime", required = false)
    private Long dataTime;

    @Text()
    private String csvValues;

    public Statistics() {
        // Default constructor for XML deserialization
    }

    Statistics(final MeasurementUnit measurementUnit,
            final int count,
            final int grid,
            final Long dataTime,
            final String csvValues) {
        this.measurementUnit = measurementUnit;
        this.count = count;
        this.grid = grid;
        this.dataTime = dataTime;
        this.csvValues = csvValues;
    }

    public int getCount() {
        return count;
    }

    public int getGrid() {
        return grid;
    }

    /**
     * Get the raw timestamp in seconds since epoch.
     * 
     * @return raw timestamp or {@code null} if not available
     */
    public Long getDataTimeRaw() {
        return dataTime;
    }

    /**
     * Get the timestamp.
     * 
     * @return timestamp or {@code null} if not available
     */
    public Instant getDataTime() {
        return Optional.ofNullable(getDataTimeRaw()) //
                .map(Instant::ofEpochSecond) //
                .orElse(null);
    }

    /**
     * Provide the gathered data as provided by Fritz!Box
     * 
     * @return data provided by the Fritz!Box
     */
    public String getCsvValues() {
        return csvValues;
    }

    /**
     * Just for unit test provided. Therefore, it is set to package private.
     */
    void setCsvValues(final String csvValues) {
        this.csvValues = csvValues;
    }

    /**
     * Provide the gathered data as computed as meant to be used by AVM
     * 
     * @return the gathered data
     */
    public List<Optional<Number>> getValues() {
        if (getCsvValues() == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(getCsvValues().split(","))
                .map(aValue -> Optional.ofNullable(computeValue(aValue)))
                .toList();
    }

    /**
     * Provide the measurement unit to be used with the statistics.
     * <p>
     * Consists of:
     * <ul>
     * <li>measurement unit [V, W, Wh, %]</li>
     * <li>precision as double to multiply with the gathered Integer</li>
     * </ul>
     * Sample: The Voltage is measured in 'V' (Volt) and has a precision of '0.001'. The number 237123 provided by the
     * statistics must be multiplied by the precision which gives us 237.123 V.
     * 
     * @return the measurement unit
     */
    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(final MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    protected Number computeValue(final String aValue) {
        if (StringHelper.isIntegerNumber(aValue)) {
            final Integer intValue = Integer.valueOf(aValue.trim());
            if (measurementUnit.getPrecision() instanceof Double precision) {
                return intValue * precision;
            } else {
                return intValue * (Integer) measurementUnit.getPrecision();
            }
        }
        return null;
    }
}
