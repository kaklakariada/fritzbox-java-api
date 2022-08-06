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
 * Sample driver for Blind
 * 
 * @author Junker Martin
 *
 */
public class TestBlind extends AbstractTestHelper {

    private static final Logger LOG = LoggerFactory.getLogger(TestBlind.class);
    private static final int WAIT_SECONDS = 20;

    private final HomeAutomation homeAutomation;

    public TestBlind() throws InterruptedException {
        this.homeAutomation = HomeAutomation.connect(Config.read());

        // make sure to set back blind to original state
        final List<Device> blindDevices = getBlindDevices();
        if (blindDevices.isEmpty()) {
            LOG.warn("No blind devices found");
            return;
        }
        final int wasPercenClosed = blindDevices.get(0).getLevelControl().getLevelpercentage();

        toggleBlindOpenClose();

        togglePercentOpen();

        setPercentOpen(blindDevices.get(0), wasPercenClosed);
    }

    private void toggleBlindOpenClose() throws InterruptedException {
        // Start first move
        LOG.info("");
        LOG.info("Initial setting");
        List<Device> blindDevices = getBlindDevices();
        if (blindDevices.isEmpty()) {
            return;
        }
        showStatus(blindDevices);
        toggleBlindOpenClose(blindDevices.get(0));

        // Start move back
        sleep();
        LOG.info("");
        LOG.info("Status after change");
        blindDevices = getBlindDevices();
        showStatus(blindDevices);
        toggleBlindOpenClose(blindDevices.get(0));

        // Show status at end of test
        LOG.info("");
        sleep();
        blindDevices = getBlindDevices();
        showStatus(blindDevices);
    }

    private void sleep() throws InterruptedException {
        LOG.info("Wait {} seconds", WAIT_SECONDS);
        Thread.sleep(WAIT_SECONDS * 1000L);
    }

    private void toggleBlindOpenClose(final Device blind) {
        final String ain = getAin(blind.getIdentifier());
        final boolean wasOpen = blind.getLevelControl().getLevel() == 0;
        final String newStatus = wasOpen ? "close" : "open";

        LOG.info("");
        LOG.info("Changing status of blind {} (ain='{}') to {}", blind.getName(), ain,
                newStatus);
        homeAutomation.setBlind(ain, newStatus);
    }

    private void togglePercentOpen() throws InterruptedException {
        // Start first move
        LOG.info("");
        LOG.info("Initial setting");
        List<Device> blindDevices = getBlindDevices();
        if (blindDevices.isEmpty()) {
            return;
        }
        showStatus(blindDevices);
        final int wasPercenClosed = blindDevices.get(0).getLevelControl().getLevelpercentage();
        final int newPercenClosed = wasPercenClosed == 0 ? 50 : wasPercenClosed / 2;

        setPercentOpen(blindDevices.get(0), newPercenClosed);

        sleep();
        LOG.info("");
        LOG.info("Status after change");
        blindDevices = getBlindDevices();
        showStatus(blindDevices);
        setPercentOpen(blindDevices.get(0), wasPercenClosed);

        // Show status at end of test
        LOG.info("");
        sleep();
        blindDevices = getBlindDevices();
        showStatus(blindDevices);
    }

    private void setPercentOpen(final Device blind, final int percent) {
        final String ain = getAin(blind.getIdentifier());
        final String newLevel = String.valueOf(percent);

        LOG.info("");
        LOG.info("Changing status of blind {} (ain='{}') to {}", blind.getName(), ain,
                newLevel);
        homeAutomation.setLevelPercentage(ain, newLevel);
    }

    private List<Device> getBlindDevices() {
        final DeviceList devices = homeAutomation.getDeviceListInfos();
        return devices.getDevices()
                .stream()
                .filter(device -> device.getBlind() != null)
                .collect(Collectors.toList());
    }

    private void showStatus(final List<Device> blindDevices) {
        blindDevices.forEach(blind -> {
            final String message = String.format("%-15s Mode: %s Percent-Closed: %s%%",
                    blind.getName(),
                    blind.getBlind().getMode(),
                    blind.getLevelControl().getLevelpercentage());
            LOG.info(message);
        });
    }

    public static void main(final String[] args) throws InterruptedException {
        new TestBlind();
    }
}
