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
import java.util.Optional;

import com.github.kaklakariada.fritzbox.Config;
import com.github.kaklakariada.fritzbox.EnergyStatisticsService;
import com.github.kaklakariada.fritzbox.HomeAutomation;
import com.github.kaklakariada.fritzbox.model.homeautomation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.fritzbox.EnergyStatisticsService.EnergyStatsTimeRange;

public class TestDriver {
    private static final Logger LOG = LoggerFactory.getLogger(TestDriver.class);

    public static void main(final String[] args) throws InterruptedException {
        final Config config = Config.read();
        final HomeAutomation homeAutomation = HomeAutomation.connect(config);

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

        testEnergyStats(homeAutomation, devices.getDevices().getFirst().getId());
        if (!ids.isEmpty()) {
            final String ain = ids.getFirst();
            testEnergyStatsNew(homeAutomation, ain);
            testVoltageStatsNew(homeAutomation, ain);
            testHomeAutomation(homeAutomation, ain);
        }
    }

    private static void testEnergyStats(final HomeAutomation homeAutomation, final String deviceId) {
        final EnergyStatisticsService service = homeAutomation.getEnergyStatistics();
        for (final EnergyStatsTimeRange timeRange : EnergyStatsTimeRange.values()) {
            final String energyStatistics = service.getEnergyStatistics(deviceId, timeRange);
            LOG.debug("Statistics {}: {}", timeRange, energyStatistics);
        }
    }

    private static void testEnergyStatsNew(final HomeAutomation homeAutomation, final String ain) {
        final Optional<Energy> energy = homeAutomation.getBasicStatistics(ain).getEnergy();
        if (energy.isEmpty()) {
            LOG.error("No Statistics for energy consumption gathered");
            return;
        }
        final Optional<Statistics> dailyEnergy = energy.get().getStatisticsByGrid(86400);
        if (dailyEnergy.isEmpty()) {
            LOG.error("No Statistics for energy consumption 'per day' gathered");
            return;
        }
        final MeasurementUnit measurementUnit = dailyEnergy.get().getMeasurementUnit();
        final List<Optional<Number>> dailyConsumption = dailyEnergy.get().getValues();

        final StringBuilder sb = new StringBuilder();
        for (final Optional<Number> dailyValue : dailyConsumption) {
            if (dailyValue.isPresent()) {
                sb.append(dailyValue.get()).append(measurementUnit.getUnit()).append(" ");
            } else {
                sb.append("-").append(" ");
            }
        }
        LOG.debug("Statistics daily energy consumption: {}", sb);
    }

    private static void testVoltageStatsNew(final HomeAutomation homeAutomation, final String ain) {
        final Optional<Power> power = homeAutomation.getBasicStatistics(ain).getPower();
        if (power.isEmpty()) {
            LOG.error("No Statistics for power consumption gathered");
            return;
        }
        final Optional<Statistics> sixMinutesVoltage = power.get().getStatisticsByGrid(10);
        if (sixMinutesVoltage.isEmpty()) {
            LOG.error("No Statistics for power consumption 'per 10 seconds interval' gathered");
            return;
        }
        final MeasurementUnit measurementUnit = sixMinutesVoltage.get().getMeasurementUnit();
        final List<Optional<Number>> sixMinutesConsumption = sixMinutesVoltage.get().getValues();

        final StringBuilder sb = new StringBuilder();
        for (final Optional<Number> intervalValue : sixMinutesConsumption) {
            if (intervalValue.isPresent()) {
                sb.append(intervalValue.get()).append(measurementUnit.getUnit()).append(" ");
            } else {
                sb.append("-").append(" ");
            }
        }
        LOG.debug("Statistics power detected: {}", sb);
    }

    private static void testHomeAutomation(final HomeAutomation homeAutomation, final String ain)
            throws InterruptedException {
        LOG.info("Switch {} has present state '{}'", ain, homeAutomation.getSwitchPresent(ain));
        LOG.info("Switch {} has state '{}'", ain, homeAutomation.getSwitchState(ain));
        LOG.info("Switch {} uses {}W", ain, homeAutomation.getSwitchPowerWatt(ain));
        LOG.info("Switch {} has used {}Wh", ain, homeAutomation.getSwitchEnergyWattHour(ain));
        if (LOG.isInfoEnabled()) {
            LOG.info("Switch {} has name '{}'", ain, homeAutomation.getSwitchName(ain));
        }
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
}
