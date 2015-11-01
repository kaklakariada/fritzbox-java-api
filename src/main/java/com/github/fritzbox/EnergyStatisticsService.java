package com.github.fritzbox;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final FritzBoxSession fritzbox;

    public EnergyStatisticsService(FritzBoxSession fritzbox) {
        this.fritzbox = fritzbox;
    }

    public String getEnergyStatistics(String deviceId, EnergyStatsTimeRange timeRange) {
        return executeDeviceCommand(deviceId, timeRange.command);
    }

    private String executeDeviceCommand(String deviceId, String command) {
        final Map<String, String> args = new HashMap<>();
        args.put("command", command);
        args.put("id", deviceId);
        args.put("xhr", "1");
        final String result = fritzbox.getForObject(QUERY_PATH, args, String.class).trim();
        LOG.debug("Executed command '{}' for device '{}' got result '{}'", command, deviceId, result);
        return result;
    }
}
