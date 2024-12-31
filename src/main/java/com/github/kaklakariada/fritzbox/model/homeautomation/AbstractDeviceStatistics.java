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

import org.simpleframework.xml.ElementList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDeviceStatistics {

    @ElementList(name = "stats", required = false, inline = true)
    protected List<Statistics> stats;

    /**
     * Supply the Statistics gathered for a chosen grid
     * 
     * @param grid
     *                 grid
     * @return Optional - avoid NPE if no statistics present
     */
    public Optional<Statistics> getStatisticsByGrid(final int grid) {
        return getStats()
                .stream()
                .filter(stat -> stat.getGrid() == grid)
                .findAny();
    }

    /**
     * All classes implementing this abstract class need to provide a "getStats"-method
     * 
     * @return List
     */
    public List<Statistics> getStats() {
        return getStats(stats, MeasurementUnit.getMatchingMeasurementUnit(this.getClass())) ;
    }

    /**
     * AVM gathers just integer numbers. We know the precision only from documentation, it is never provided by returned
     * responses from Fritz!Box. So we add this information here to the statistics.
     * 
     * @param stats statistics to which to add the measurement unit
     * @param measurementUnit the unit to add to the statistics
     * @return statistics with measurement units
     */
    protected List<Statistics> getStats(final List<Statistics> stats, final MeasurementUnit measurementUnit) {
        if (stats == null) { return Collections.emptyList(); }
        return stats
                .stream()
                .map(stat -> {
                    stat.setMeasurementUnit(measurementUnit);
                    return stat;
                }).toList();
    }

    /**
     * All classes implementing this abstract class need to provide a "statisticsToString"-method
     * 
     * @return List
     */
    protected List<String> statisticsToString(){
        return statisticsToString(MeasurementUnit.getMatchingMeasurementUnit(this.getClass()).name());
    }

    /**
     * @param type statistics type
     * @return statistics as one line per grid
     */
    protected List<String> statisticsToString(final String type) {
        return getStats()
                .stream()
                .map(stat -> statisticsToString(type, stat)).toList();
    }

    /**
     * Form a line from a single statistic.
     * 
     * @param type statistics type
     * @param statistics statistic to convert to {@link String}
     * @return statistic as a line
     */
    private String statisticsToString(final String type, final Statistics statistics) {
        return String.format("[%s] count=%s,grid=%s values=[%s]", type, statistics.getCount(), statistics.getGrid(),
                statistics.getCsvValues());
    }
}
