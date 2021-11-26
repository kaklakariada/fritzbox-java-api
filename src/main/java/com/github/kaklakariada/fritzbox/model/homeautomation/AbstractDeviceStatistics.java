package com.github.kaklakariada.fritzbox.model.homeautomation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractDeviceStatistics {
    
    /**
     * Supply grids used to gather statistics
     * @return
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
     * @param grid
     * @return
     */
    public Optional<Statistics> getStatisticsByGrid(final int grid) {
        Optional<Statistics> statisticsByGrid = getStats()
                .stream()
                .filter(stats -> stats.getGrid() == grid)
                .findAny();
        
        return statisticsByGrid;
    };
    
    /**
     * Alle classes implementing this abstract class need to provide a "getStats"-method
     * @return
     */
    public abstract List<Statistics> getStats();
    
    /**
     * AVM gathers just integer numbers. We know the precision only from documentation, it is never provided 
     * by returned responses from Fritz!Box.
     * So we add this information here to the statistics.
     * @param stats
     * @param measurementUnit
     * @return
     */
    protected List<Statistics> getStats(final List<Statistics> stats, final MEASUREMENT_UNIT measurementUnit) {
        final List<Statistics> markedStats = stats
                .stream()
                .map(stat -> {
                    stat.setMeasurementUnit(measurementUnit);
                    return stat;
                })
                .collect(Collectors.toList());
        return markedStats;
    }
}
