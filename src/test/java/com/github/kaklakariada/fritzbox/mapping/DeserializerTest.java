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
package com.github.kaklakariada.fritzbox.mapping;

import static com.github.kaklakariada.fritzbox.assertions.HomeAutomationAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.github.kaklakariada.fritzbox.model.SessionInfo;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceStats;
import com.github.kaklakariada.fritzbox.model.homeautomation.Group;
import com.github.kaklakariada.fritzbox.model.homeautomation.GroupInfo;
import com.github.kaklakariada.fritzbox.model.homeautomation.PowerMeter;
import com.github.kaklakariada.fritzbox.model.homeautomation.SwitchState;

class DeserializerTest {

    @Test
    void parseDeviceListFritzDect200() throws IOException {
        final DeviceList deviceList = parseDeviceList(
                Paths.get("src/test/resources/deviceListConnectedFritzDect200Payload.xml"));
        assertThat(deviceList.getDevices()).hasSize(1);
    }

    @Test
    void parseDeviceListFritzDect301() throws IOException {
        final DeviceList deviceList = parseDeviceList(
                Paths.get("src/test/resources/deviceListConnectedFritzDect301Payload.xml"));
        assertThat(deviceList.getDevices()).hasSize(1);
    }

    @Test
    void parseDeviceListNotConnectedFritzDect500() throws IOException {
        final DeviceList deviceList = parseDeviceList(
                Paths.get("src/test/resources/deviceListNotConnectedFritzDect500Payload.xml"));
        assertThat(deviceList.getDevices()).hasSize(1);
    }

    @Test
    void parseDeviceListConnectedFritzDect500() throws IOException {
        final DeviceList deviceList = parseDeviceList(
                Paths.get("src/test/resources/deviceListConnectedFritzDect500Payload.xml"));
        assertThat(deviceList.getDevices()).hasSize(1);
    }

    @Test
    void parseDeviceListConnectedFritzDect440() throws IOException {
        final DeviceList deviceList = parseDeviceList(
                Paths.get("src/test/resources/deviceListConnectedFritzDect440Payload.xml"));
        assertThat(deviceList.getDevices()).hasSize(1);
    }

    @Test
    void parseDeviceListConnectedFritzDect440x2() throws IOException {
        final DeviceList deviceList = parseDeviceList(
                Paths.get("src/test/resources/deviceListConnectedFritzDect440x2Payload.xml"));
        assertThat(deviceList.getDevices()).hasSize(2);
    }

    @Test
    void parseDeviceListAllTogether() throws IOException {
        final DeviceList deviceList = parseDeviceList(Paths.get("src/test/resources/deviceListAllTogetherPayload.xml"));
        assertThat(deviceList.getDevices()).hasSize(5);
    }

    @Test
    void parseDeviceListEmpty() throws IOException {
        final DeviceList deviceList = parseDeviceList(Paths.get("src/test/resources/FritzOS29/deviceListEmpty.xml"));
        assertThat(deviceList.getDevices()).isEmpty();
    }

    @Test
    void parseDeviceList() throws IOException {
        final DeviceList deviceList = parseDeviceList(Paths.get("src/test/resources/deviceList.xml"));
        assertThat(deviceList.getDevices()).hasSize(16);
    }

    @Test
    void parseDeviceListAllTogetherWithBlind() throws IOException {
        final DeviceList deviceList = parseDeviceList(
                Paths.get("src/test/resources/FritzOS29/deviceListAllTogetherWithBlind.xml"));
        assertThat(deviceList.getDevices()).hasSize(8);
    }

    DeviceList parseDeviceList(final Path file) throws IOException {
        final InputStream content = Files.newInputStream(file);
        return new Deserializer().parse(content, DeviceList.class);
    }

    @Test
    void parseDeviceStatsFritzDect200() throws IOException {
        final InputStream fileContent = Files
                .newInputStream(Paths.get("src/test/resources/FritzOS29/devicestatsFritzDect200.xml"));
        final DeviceStats stats = new Deserializer().parse(fileContent, DeviceStats.class);
        assertEquals(1, stats.getTemperature().get().getStats().size(), "Temperature has just one statistics Element");
        assertEquals(0.1,
                stats.getTemperature().get().getStats().getFirst().getMeasurementUnit().getPrecision(),
                "Temperature statistics have unit precision '0.1'");

        assertEquals(2, stats.getEnergy().get().getStats().size(), "Energy has two statistics Element");

        assertFalse(stats.getHumidity().isPresent(), "Humidity is missing");

        assertEquals(1665897036, (long) stats.getEnergy().get().getStats().getFirst().getDataTimeRaw(),
                "DataTime (raw) is missing");
        assertNotEquals(1665897, stats.getEnergy().get().getStats().getFirst().getDataTime().toEpochMilli(),
                "DataTime is missing");
    }

    @Test
    void parseSessionInfo() throws IOException {
        final InputStream fileContent = Files.newInputStream(Paths.get("src/test/resources/sessionInfo.xml"));
        final SessionInfo sessionInfo = new Deserializer().parse(fileContent, SessionInfo.class);
        assertNotNull(sessionInfo.getUsers());
        assertEquals(3, sessionInfo.getUsers().size());
        assertEquals("UserA", sessionInfo.getUsers().get(0).getName());
        assertFalse(sessionInfo.getUsers().get(0).isLast());
        assertEquals("UserB", sessionInfo.getUsers().get(1).getName());
        assertFalse(sessionInfo.getUsers().get(1).isLast());
        assertEquals("UserC", sessionInfo.getUsers().get(2).getName());
        assertTrue(sessionInfo.getUsers().get(2).isLast());
    }

    void parseDeviceGroup() throws IOException {
        // given
        final Group group = getDeviceList6840().getGroupById("900");
        // then
        assertThat(group)
                .hasName("PV")
                .hasPresent("1");
    }

    @Test
    void parseDeviceGroupSwitch() throws IOException {
        // given
        final Group group = getDeviceList6840().getGroupById("900");

        // when
        final SwitchState switchState = group.getSwitchState();

        // then
        assertThat(switchState)
                .isOn(true)
                .isDeviceLocked(false)
                .isLocked(false)
                .hasMode(SwitchState.SwitchMode.MANUAL);

    }

    @Test
    void parseDeviceGroupPowerMeter() throws IOException {
        // given
        final Group group = getDeviceList6840().getGroupById("900");

        // when
        final PowerMeter powerMeter = group.getPowerMeter();

        // then
        assertThat(powerMeter)
                .hasEnergyWattHours(875112)
                .hasPowerMilliWatt(38480);

    }

    @Test
    void parseDeviceGroupGroupInfo() throws IOException {
        // given
        final Group group = getDeviceList6840().getGroupById("900");

        // when
        final GroupInfo groupInfo = group.getGroupInfo();

        // then
        assertThat(groupInfo)
                .hasMasterDeviceId("0")
                .containsAllMembers("16");

    }

    DeviceList getDeviceList6840() throws IOException {
        return parseDeviceList(Paths.get("src/test/resources/devicelist6840.xml"));
    }
}
