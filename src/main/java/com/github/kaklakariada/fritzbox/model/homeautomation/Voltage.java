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

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "voltage")
public class Voltage extends AbstractDeviceStatistics {
    
    @ElementList(name = "stats", required = false, inline = true)
    private List<Statistics> stats;

    public List<Statistics> getStats() {
        return getStats(stats, MeasurementUnit.getMatchingMeasurementUnit(this.getClass())) ;
    }
    
    @Override
    protected List<String> statisticsToString() {
        return statisticsToString(MeasurementUnit.getMatchingMeasurementUnit(this.getClass()).name());
    }
}
