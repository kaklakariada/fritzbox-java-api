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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.fritzbox.EnergyStatisticsService.EnergyStatsTimeRange;
import com.github.kaklakariada.fritzbox.model.homeautomation.Device;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;
import com.github.kaklakariada.fritzbox.model.homeautomation.PowerMeter;

/**
 * this class is a test Driver for the home automation
 *
 */
public class TestDriver {
  private final static Logger LOG = LoggerFactory.getLogger(TestDriver.class);

  /**
   * main class
   * 
   * @param args
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {
    final Config config = ConfigService.readConfig();

    LOG.info("Logging in to '{}' with username '{}'", config.baseUrl,
        config.username);
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

    for (final String ain : ids) {
      testHomeAutomation(homeAutomation, ain);
      // testEnergyStats(homeAutomation.getSession(), ain);
    }
  }

  /**
   * test the energy statistics
   * 
   * @param session
   * @param deviceId
   */
  private static void testEnergyStats(FritzBoxSessionImpl session,
      String deviceId) {
    final EnergyStatisticsService service = new EnergyStatisticsService(
        session);
    for (final EnergyStatsTimeRange timeRange : EnergyStatsTimeRange.values()) {
      final String energyStatistics = service.getEnergyStatistics(deviceId,
          timeRange);
      LOG.debug("Statistics {}: {}", timeRange, energyStatistics);
    }
  }

  /**
   * test the home automation
   * 
   * @param homeAutomation
   * @param ain
   */
  private static void testHomeAutomation(final HomeAutomation homeAutomation,
      final String ain) {
    // homeAutomation.switchPowerState(ain, false);
    // homeAutomation.togglePowerState(ain);
    LOG.info("Switch {} ", ain);
    LOG.info("\t has present state '{}'", homeAutomation.getSwitchPresent(ain));
    LOG.info("\t has state '{}'", homeAutomation.getSwitchState(ain));
    LOG.info("\t uses {} W", homeAutomation.getSwitchPowerWatt(ain));
    LOG.info("\t has used {} Wh", homeAutomation.getSwitchEnergyWattHour(ain));
    LOG.info("\t has name '{}'", homeAutomation.getSwitchName(ain));
    LOG.info("\t has temperature {} °C", homeAutomation.getTemperature(ain));
    LOG.info("---");
  }

  /**
   * test endless
   * 
   * @param homeAutomation
   * @throws Exception
   */
  public void testEndless(HomeAutomation homeAutomation) throws Exception {
    while (true) {
      final Device device = homeAutomation.getDeviceListInfos().getDevices()
          .get(0);
      final PowerMeter powerMeter = device.getPowerMeter();
      LOG.debug("State: {}, temp: {}°C, power: {}W, energy: {}Wh",
          device.getSwitchState().isOn() ? "on" : "off",
          device.getTemperature().getCelsius(), powerMeter.getPowerWatt(),
          powerMeter.getEnergyWattHours());
      Thread.sleep(1000);
    }
  }

}
