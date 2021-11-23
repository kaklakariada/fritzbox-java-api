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

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "stats")
public class Statistics {

    private MEASUREMENT_UNIT measurementUnit;

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

    public String getCsvValues() {
        return csvValues;
    }

    public MEASUREMENT_UNIT getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(final MEASUREMENT_UNIT measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
}