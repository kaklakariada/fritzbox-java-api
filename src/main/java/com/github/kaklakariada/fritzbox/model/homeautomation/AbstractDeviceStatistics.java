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
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractDeviceStatistics {

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
                .filter(stats -> stats.getGrid() == grid)
                .findAny();
    }

    /**
     * All classes implementing this abstract class need to provide a "getStats"-method
     * 
     * @return List
     */
    public abstract List<Statistics> getStats();

    /**
     * AVM gathers just integer numbers. We know the precision only from documentation, it is never provided by returned
     * responses from Fritz!Box. So we add this information here to the statistics.
     * 
     * @param stats statistics to which to add the measurment unit
     * @param measurementUnit the unit to add to the statistics
     * @return statistics with measurement units
     */
    protected List<Statistics> getStats(final List<Statistics> stats, final MeasurementUnit measurementUnit) {
        return stats
                .stream()
                .map(stat -> {
                    stat.setMeasurementUnit(measurementUnit);
                    return stat;
                })
                .collect(Collectors.toList());
    }

    /**
     * All classes implementing this abstract class need to provide a "statisticsToString"-method
     * 
     * @return List
     */
    protected abstract List<String> statisticsToString();

    /**
     * @param type statistics type
     * @return statistics as one line per grid
     */
    protected List<String> statisticsToString(final String type) {
        return getStats()
                .stream()
                .map(stats -> statisticsToString(type, stats))
                .collect(Collectors.toList());
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
