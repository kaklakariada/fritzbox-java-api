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
     * Supply grids used to gather statistics
     * @return List<Integer> 
     */
    public List<Integer> getGridList() {
        final List<Integer> gridList = getStats()
                .stream()
                .map(stats -> stats.getGrid())
                .collect(Collectors.toList());
        
        return gridList;
    };

    /**
     * Supply the Statistics gathered for a chosen grid
     * @param int grid
     * @return Optional<Statistics> - avoid NPE is no statistics present
     */
    public Optional<Statistics> getStatisticsByGrid(final int grid) {
        Optional<Statistics> statisticsByGrid = getStats()
                .stream()
                .filter(stats -> stats.getGrid() == grid)
                .findAny();
        
        return statisticsByGrid;
    };
    
    /**
     * All classes implementing this abstract class need to provide a "getStats"-method
     * @return List<Statistics>
     */
    public abstract List<Statistics> getStats();
    
    /**
     * AVM gathers just integer numbers. We know the precision only from documentation, it is never provided 
     * by returned responses from Fritz!Box.
     * So we add this information here to the statistics.
     * @param stats
     * @param measurementUnit
     * @return List<Statistics>
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
     * @return List<Statistics>
     */
    abstract protected List<String> statisticsToString();
    
    /**
     * @return statistics as one line per grid
     */
    protected List<String> statisticsToString(final String type) {
        return  getStats()
                .stream()
                .map(stats -> statisticsToString(type, stats))
                .collect(Collectors.toList());
    }
    
    /**
     * form a line from a single statistic
     * @param type
     * @param statistics
     * @return statistic as a line
     */
    protected String statisticsToString(final String type, final Statistics statistics) {
        return String.format("[%s] count=%s,grid=%s values=[%s]", type, statistics.getCount(), statistics.getGrid(), statistics.getCsvValues());
    }
}
