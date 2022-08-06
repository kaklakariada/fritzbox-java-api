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
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.fritzbox.model.homeautomation.Device;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;

/**
 * Sample driver for Hkr "Heizkörperregler(DE), Radiator regulator(EN)"
 * 
 * @author Junker Martin
 *
 */
public class TestHkrDriver extends AbstractTestHelper {

    private static final Logger LOG = LoggerFactory.getLogger(TestHkrDriver.class);

    private final HomeAutomation homeAutomation;

    public TestHkrDriver() {
        this.homeAutomation = HomeAutomation.connect(Config.read());

        LOG.info("");
        LOG.info("Initial temperature");
        List<Device> hkrDevices = getHkrDevices();

        if (hkrDevices.isEmpty()) {
            LOG.warn("No HKR devices found");
            return;
        }
        showTemperatures(hkrDevices);

        final String ain = hkrDevices.get(0).getIdentifier().replaceAll("\\s*", "");
        final double wasTemperature = getCelsius(hkrDevices.get(0).getHkr().getTsoll());
        final double newTsoll = 25D;
        LOG.info("");
        LOG.info("Changing temperature of {} (ain='{}')to {} degrees", hkrDevices.get(0).getName(), ain,
                newTsoll);
        homeAutomation.setHkrTsoll(ain, String.valueOf(getDegreeCode(newTsoll)));

        LOG.info("");
        LOG.info("Temperature after change");
        hkrDevices = getHkrDevices();
        showTemperatures(hkrDevices);

        homeAutomation.setHkrTsoll(ain, String.valueOf(getDegreeCode(wasTemperature)));
        LOG.info("");
        LOG.info("Changing back temperature of {} (ain='{}')to {} degrees", hkrDevices.get(0).getName(), ain,
                wasTemperature);
        LOG.info("");
        LOG.info("Temperature after changing back");
        hkrDevices = getHkrDevices();
        showTemperatures(hkrDevices);
    }

    private List<Device> getHkrDevices() {
        final DeviceList devices = homeAutomation.getDeviceListInfos();
        final List<Device> hkrDevices = devices.getDevices()
                .stream()
                .filter(device -> device.getHkr() != null)
                .collect(Collectors.toList());
        return hkrDevices;
    }

    private void showTemperatures(final List<Device> hkrDevices) {
        hkrDevices.forEach(hkr -> {
            final String message = String.format("%-15s tist: %s(%s\u00B0), tsoll: %s(%s\u00B0)",
                    hkr.getName(),
                    hkr.getHkr().getTist(), getCelsius(hkr.getHkr().getTist()),
                    hkr.getHkr().getTsoll(), getCelsius(hkr.getHkr().getTsoll()));
            LOG.info(message);
        });
    }

    public static void main(final String[] args) {
        new TestHkrDriver();
    }

}
