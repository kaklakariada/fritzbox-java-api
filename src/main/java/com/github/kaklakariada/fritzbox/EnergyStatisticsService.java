package com.github.kaklakariada.fritzbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.fritzbox.http.QueryParameters;

/**
 * This class retrieves energy statistics.
 */
public class EnergyStatisticsService {

    private final static Logger LOG = LoggerFactory.getLogger(EnergyStatisticsService.class);
    private final static String QUERY_PATH = "/net/home_auto_query.lua";

    public static enum EnergyStatsTimeRange {
        TEN_MINUTES("EnergyStats_10"), //
        ONE_HOUR("EnergyStats_hour"), //
        ONE_DAY("EnergyStats_24h"), //
        ONE_WEEK("EnergyStats_week"), //
        ONE_MONTH("EnergyStats_month"), //
        ONE_YEAR("EnergyStats_year");

        private final String command;

        private EnergyStatsTimeRange(String command) {
            this.command = command;
        }
    }

    private final FritzBoxSession session;

    public EnergyStatisticsService(FritzBoxSession session) {
        this.session = session;
    }

    public String getEnergyStatistics(String deviceId, EnergyStatsTimeRange timeRange) {
        return executeDeviceCommand(deviceId, timeRange.command);
    }

    private String executeDeviceCommand(String deviceId, String command) {
        final QueryParameters parameters = QueryParameters.builder().add("command", command).add("id", deviceId)
                .add("xhr", "1").build();
        final String statisticsJson = session.getAutenticated(QUERY_PATH, parameters, String.class);
        LOG.trace("Got statistics json for command '{}': {}", command, statisticsJson);
        return statisticsJson;
    }
}
