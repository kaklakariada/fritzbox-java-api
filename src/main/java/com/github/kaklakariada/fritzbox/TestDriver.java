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
package com.github.kaklakariada.fritzbox;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.fritzbox.EnergyStatisticsService.EnergyStatsTimeRange;
import com.github.kaklakariada.fritzbox.model.homeautomation.AbstractDeviceStatistics;
import com.github.kaklakariada.fritzbox.model.homeautomation.Device;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;
import com.github.kaklakariada.fritzbox.model.homeautomation.Energy;
import com.github.kaklakariada.fritzbox.model.homeautomation.MEASUREMENT_UNIT;
import com.github.kaklakariada.fritzbox.model.homeautomation.PowerMeter;
import com.github.kaklakariada.fritzbox.model.homeautomation.Statistics;

public class TestDriver {
    private static final Logger LOG = LoggerFactory.getLogger(TestDriver.class);

    public static void main(String[] args) throws InterruptedException {
        final Properties config = readConfig(Paths.get("application.properties"));
        final String url = config.getProperty("fritzbox.url");
        final String username = config.getProperty("fritzbox.username", null);
        final String password = config.getProperty("fritzbox.password");

        LOG.info("Logging in to '{}' with username '{}'", url, username);
        final HomeAutomation homeAutomation = HomeAutomation.connect(url, username, password);

        final DeviceList devices = homeAutomation.getDeviceListInfos();
        LOG.info("Found {} devices", devices.getDevices().size());
        for (final Device device : devices.getDevices()) {
            LOG.info("\t{}", device);
        }

        final List<String> ids = homeAutomation.getSwitchList();
        LOG.info("Found {} device ids: {}", ids.size(), ids);

        if (devices.getDevices().isEmpty()) {
            homeAutomation.logout();
            return;
        }

        final String ain = ids.get(0);

        // testEnergyStats(homeAutomation, devices.getDevices().get(0).getId());
        testEnergyStatsNew(homeAutomation, ain);
        testVoltageStatsNew(homeAutomation, ain);
        testHomeAutomation(homeAutomation, ain);
    }

    private static void testEnergyStats(HomeAutomation homeAutomation, String deviceId) {
        final EnergyStatisticsService service = homeAutomation.getEnergyStatistics();
        for (final EnergyStatsTimeRange timeRange : EnergyStatsTimeRange.values()) {
            final String energyStatistics = service.getEnergyStatistics(deviceId, timeRange);
            LOG.debug("Statistics {}: {}", timeRange, energyStatistics);
        }
    }

    private static void testEnergyStatsNew(HomeAutomation homeAutomation, String ain) {
        final Optional<AbstractDeviceStatistics> energy = homeAutomation.getBasicStatistics(ain).getEnergy();
        if (energy.isEmpty()) {
            LOG.error("No Statistics for energy consumption gathered");
            return;
        }
        Optional<Statistics> dailyEnergy = energy.get().getStatisticsByGrid(86400);
        if (dailyEnergy.isEmpty()) {
            LOG.error("No Statistics for energy consumption 'per day' gathered");
            return;
        }
        MEASUREMENT_UNIT measurementUnit = dailyEnergy.get().getMeasurementUnit();
        List<Optional<Number>> dailyConsumption = dailyEnergy.get().getValues();

        StringBuffer sb = new StringBuffer();
        for (final Optional<Number> dailyValue : dailyConsumption) {
            if (dailyValue.isPresent()) {
            sb.append(dailyValue.get()).append(measurementUnit.getUnit()).append(" ");
            } else {
                sb.append("-").append(" ");
            }
        }
        LOG.debug("Statistics daily energy consumption: {}", sb.toString());
    }
    
    private static void testVoltageStatsNew(HomeAutomation homeAutomation, String ain) {
        final Optional<AbstractDeviceStatistics> power = homeAutomation.getBasicStatistics(ain).getPower();
        if (power.isEmpty()) {
            LOG.error("No Statistics for power consumption gathered");
            return;
        }
        Optional<Statistics> sixMinsVoltage = power.get().getStatisticsByGrid(10);
        if (sixMinsVoltage.isEmpty()) {
            LOG.error("No Statistics for power consumption 'per 10 seconds interval' gathered");
            return;
        }
        MEASUREMENT_UNIT measurementUnit = sixMinsVoltage.get().getMeasurementUnit();
        List<Optional<Number>> sixMinutestConsumption = sixMinsVoltage.get().getValues();

        StringBuffer sb = new StringBuffer();
        for (final Optional<Number> intervalValue : sixMinutestConsumption) {
            if (intervalValue.isPresent()) {
            sb.append(intervalValue.get()).append(measurementUnit.getUnit()).append(" ");
            } else {
                sb.append("-").append(" ");
            }
        }
        LOG.debug("Statistics power detetcted: {}", sb.toString());
    }


    private static void testHomeAutomation(final HomeAutomation homeAutomation, final String ain)
            throws InterruptedException {
        homeAutomation.switchPowerState(ain, false);
        homeAutomation.togglePowerState(ain);
        LOG.info("Switch {} has present state '{}'", ain, homeAutomation.getSwitchPresent(ain));
        LOG.info("Switch {} has state '{}'", ain, homeAutomation.getSwitchState(ain));
        LOG.info("Switch {} uses {}W", ain, homeAutomation.getSwitchPowerWatt(ain));
        LOG.info("Switch {} has used {}Wh", ain, homeAutomation.getSwitchEnergyWattHour(ain));
        LOG.info("Switch {} has name '{}'", ain, homeAutomation.getSwitchName(ain));
        LOG.info("Switch {} has temperature {}°C", ain, homeAutomation.getTemperature(ain));
        LOG.info("Switch {} statistics '{}'", ain, homeAutomation.getBasicStatistics(ain));

        while (true) {
            final List<Device> devices = homeAutomation.getDeviceListInfos().getDevices();
            if (devices.isEmpty()) {
                LOG.warn("No devices found");
                return;
            }
            devices.forEach(TestDriver::logDeviceDetails);
            Thread.sleep(1000);
        }
    }

    private static void logDeviceDetails(final Device device) {
        final PowerMeter powerMeter = device.getPowerMeter();
        if (device.getSwitchState() == null) {
            return;
        }
        final Object switchState = device.getSwitchState().isOn() ? "on" : "off";
        LOG.debug("State: {}, temp: {}°C, voltage: {}V, power: {}W, energy: {}Wh",
                switchState, device.getTemperature().getCelsius(), powerMeter.getVoltageVolt(),
                powerMeter.getPowerWatt(), powerMeter.getEnergyWattHours());
    }

    private static Properties readConfig(Path path) {
        final Properties config = new Properties();
        final Path absolutePath = path.toAbsolutePath();
        LOG.debug("Reading config from file {}", absolutePath);
        try (InputStream in = Files.newInputStream(absolutePath)) {
            config.load(in);
        } catch (final IOException e) {
            throw new FritzBoxException("Error loading configuration from " + absolutePath, e);
        }
        return config;
    }
}
