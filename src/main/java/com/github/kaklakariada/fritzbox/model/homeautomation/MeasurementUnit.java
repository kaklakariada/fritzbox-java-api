/**
 * A Java API for managing FritzBox HomeAutomation
 * Copyright (C) 2017 Christoph Pirkl <christoph at users.sourceforge.net>
 * <br>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <br>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <br>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.kaklakariada.fritzbox.model.homeautomation;

import com.github.kaklakariada.fritzbox.MissingClassException;

/**
 * provide the measurement unit to be used with the statistics.<p>
 * Consists of:
 * <ul>
 * <li>measurement unit [V, W, Wh, %]</li>
 * <li>precision as double to multiply with the gathered Integer value
 * </ul>
 * Sample: The Voltage is measured in 'V' (Volt) and has a precision of '0.001'. The number 237123 provided
 * by the statistics must be multiplied by the precision which gives us 237.123 V.
 * 
 * @author Ulrich Schmidt(Gombers)
 *
 */
public enum MeasurementUnit {
    TEMPERATURE("C", 0.1, Temperature.class), 
    VOLTAGE("V", 0.001, Voltage.class), 
    POWER("W", 0.01, Power.class), 
    ENERGY("Wh", 1, Energy.class), 
    HUMIDITY("%", 1, Humidity.class);

    private final String unit;
    private final Number precision;
    private final Class<?> mapper;

    MeasurementUnit(String unit, Number precision, Class<?> mapper) {
        this.unit = unit;
        this.precision = precision;
        this.mapper = mapper;
    }

    public String getUnit() {
        return unit;
    }

    public Number getPrecision() {
        return precision;
    }

    public Class<?> getMapper() {
        return mapper;
    }

    public static MeasurementUnit getMatchingMeasurementUnit(Class<?> caller) {
        for (MeasurementUnit measurementUnit : MeasurementUnit.values()) {
            if (caller.isAssignableFrom(measurementUnit.getMapper())) {
                return measurementUnit;
            }
        }
        throw  new MissingClassException(String.format("Could not detect enum of type 'MeasurementUnit' associated to class '%s'", caller.toString()));
    }
}
