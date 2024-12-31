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
package com.github.kaklakariada.fritzbox.app;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.fritzbox.Config;
import com.github.kaklakariada.fritzbox.HomeAutomation;
import com.github.kaklakariada.fritzbox.model.homeautomation.*;

public class TestTemperature {
    private static final Logger LOG = LoggerFactory.getLogger(TestTemperature.class);

    public static void main(final String[] args) {
        final HomeAutomation homeAutomation = HomeAutomation.connect(Config.read());

        getCurrentTemperature(homeAutomation);
        getTemperatureStatistics(homeAutomation);

        homeAutomation.logout();
    }

    private static void getCurrentTemperature(final HomeAutomation homeAutomation) {
        final DeviceList devices = homeAutomation.getDeviceListInfos();
        LOG.info("Found {} devices", devices.getDevices().size());
        for (final Device d : devices.getDevices()) {
            final Temperature t = d.getTemperature();
            if (t != null) {
                final Humidity h = d.getHumidity();
                LOG.info("{}: {}°C {}%rH", d.getName(), t.getCelsius(), h != null ? h.getRelHumidity() : null);
            }
        }
    }

    private static void getTemperatureStatistics(final HomeAutomation homeAutomation) {
        final DeviceList l = homeAutomation.getDeviceListInfos();
        for (final Device d : l.getDevices()) {
            LOG.info("d {} : ain {}", d.getName(), d.getIdentifier());
            final DeviceStats s = homeAutomation.getBasicStatistics(d.getIdentifier());
            s.getTemperature().ifPresent(TestTemperature::temperature);
            s.getHumidity().ifPresent(TestTemperature::humidity);
        }
    }

    private static void temperature(final @NotNull AbstractDeviceStatistics t) {
        final List<Statistics> ts = t.getStats();
        if (ts != null) {
            for (int i = 0; i < ts.size(); i++) {
                final Statistics tsi = ts.get(i);
                LOG.info("ts {}: {} // {}", i, tsi.getDataTime(), tsi.getCsvValues());
            }
        }
    }

    private static void humidity(final @NotNull AbstractDeviceStatistics h) {
        final List<Statistics> hs = h.getStats();
        if (hs != null) {
            for (int i = 0; i < hs.size(); i++) {
                final Statistics hsi = hs.get(i);
                LOG.info("hs {}: {} // {}", i, hsi.getDataTime(), hsi.getCsvValues());
            }
        }
    }
}
