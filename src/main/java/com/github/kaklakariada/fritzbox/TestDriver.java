/**
 * A Java API for managing FritzBox HomeAutomation
 * Copyright (C) 2015 Christoph Pirkl <christoph at users.sourceforge.net>
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
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.fritzbox.EnergyStatisticsService.EnergyStatsTimeRange;
import com.github.kaklakariada.fritzbox.http.HttpTemplate;
import com.github.kaklakariada.fritzbox.model.homeautomation.Device;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;
import com.github.kaklakariada.fritzbox.model.homeautomation.PowerMeter;

public class TestDriver {
    private final static Logger LOG = LoggerFactory.getLogger(TestDriver.class);

    public static void main(String[] args) throws InterruptedException {
        final Properties config = readConfig(Paths.get("application.properties"));
        final String url = config.getProperty("fritzbox.url");
        final String username = config.getProperty("fritzbox.username", null);
        final String password = config.getProperty("fritzbox.password");

        LOG.info("Logging in to {} with username {}", url, username);
        final HttpTemplate template = new HttpTemplate(url);
        final FritzBoxSession session = new FritzBoxSession(template);
        session.login(username, password);
        final HomeAutomation homeAutomation = new HomeAutomation(session);

        final DeviceList devices = homeAutomation.getDeviceListInfos();
        LOG.info("Found {} devices", devices.getDevices().size());
        for (final Device device : devices.getDevices()) {
            LOG.info("\t{}", device);
        }

        final List<String> ids = homeAutomation.getSwitchList();
        LOG.info("Found {} device ids: {}", ids.size(), ids);

        if (devices.getDevices().isEmpty()) {
            session.logout();
            return;
        }

        final String ain = ids.get(0);

        // testEnergyStats(session, devices.getDevices().get(0).getId());
        testHomeAutomation(homeAutomation, ain);
    }

    private static void testEnergyStats(FritzBoxSession session, String deviceId) {
        final EnergyStatisticsService service = new EnergyStatisticsService(session);
        for (final EnergyStatsTimeRange timeRange : EnergyStatsTimeRange.values()) {
            final String energyStatistics = service.getEnergyStatistics(deviceId, timeRange);
            LOG.debug("Statistics {}: {}", timeRange, energyStatistics);
        }
    }

    private static void testHomeAutomation(final HomeAutomation homeAutomation, final String ain)
            throws InterruptedException {
        // homeAutomation.switchPowerState(ain, false);
        // homeAutomation.togglePowerState(ain);
        LOG.info("Switch {} has present state '{}'", ain, homeAutomation.getSwitchPresent(ain));
        LOG.info("Switch {} has state '{}'", ain, homeAutomation.getSwitchState(ain));
        LOG.info("Switch {} uses {}W", ain, homeAutomation.getSwitchPowerWatt(ain));
        LOG.info("Switch {} has used {}Wh", ain, homeAutomation.getSwitchEnergyWattHour(ain));
        LOG.info("Switch {} has name '{}'", ain, homeAutomation.getSwitchName(ain));
        LOG.info("Switch {} has temperature {}°C", ain, homeAutomation.getTemperature(ain));

        while (true) {
            final Device device = homeAutomation.getDeviceListInfos().getDevices().get(0);
            final PowerMeter powerMeter = device.getPowerMeter();
            LOG.debug("State: {}, temp: {}°C, power: {}W, energy: {}Wh", device.getSwitchState().isOn() ? "on" : "off",
                    device.getTemperature().getCelsius(), powerMeter.getPowerWatt(), powerMeter.getEnergyWattHours());
            Thread.sleep(1000);
        }
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
