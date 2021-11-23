package com.github.kaklakariada.fritzbox.model.homeautomation;

import java.util.List;
import java.util.stream.Collectors;

public interface StatisticsInterface {
    default List<Statistics> getStats(List<Statistics> stats, Class caller) {
        
        final List<Statistics> markedStats = stats
                .stream()
                .map(stat -> {
                    stat.setMeasurementUnit(MEASUREMENT_UNIT.getMatchingMeasurementUnit(caller));
                    return stat;
                })
                .collect(Collectors.toList());
        return markedStats;
    }

}
