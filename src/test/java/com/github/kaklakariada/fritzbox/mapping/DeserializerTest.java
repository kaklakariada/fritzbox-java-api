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
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.kaklakariada.fritzbox.model.SessionInfo;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceStats;
import com.github.kaklakariada.fritzbox.model.homeautomation.Group;
import com.github.kaklakariada.fritzbox.model.homeautomation.GroupInfo;
import com.github.kaklakariada.fritzbox.model.homeautomation.PowerMeter;
import com.github.kaklakariada.fritzbox.model.homeautomation.SwitchState;

public class DeserializerTest {

    private static DeviceList deviceList6840 = null;

    @BeforeAll
    public static void setupDeviceList6840() throws IOException {
        final String fileContent6840 = String.join("\n",
                Files.readAllLines(Paths.get("src/test/resources/devicelist6840.xml")));
        deviceList6840 = new Deserializer().parse(fileContent6840, DeviceList.class);
        assertThat(deviceList6840).hasGroupsSize(2);
    }

    @Test
    public void parseDeviceListFritzDect200() throws IOException {
        final String fileContent = Files
                .readAllLines(Paths.get("src/test/resources/deviceListConnectedFritzDect200Payload.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceListFritzDect301() throws IOException {
        final String fileContent = Files
                .readAllLines(Paths.get("src/test/resources/deviceListConnectedFritzDect200Payload.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceListNotConnectedFritzDect500() throws IOException {
        final String fileContent = Files
                .readAllLines(Paths.get("src/test/resources/deviceListNotConnectedFritzDect500Payload.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceListConnectedFritzDect500() throws IOException {
        final String fileContent = Files
                .readAllLines(Paths.get("src/test/resources/deviceListConnectedFritzDect500Payload.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceListConnectedFritzDect440() throws IOException {
        final String fileContent = Files
                .readAllLines(Paths.get("src/test/resources/deviceListConnectedFritzDect440Payload.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceListConnectedFritzDect440x2() throws IOException {
        final String fileContent = Files
                .readAllLines(Paths.get("src/test/resources/deviceListConnectedFritzDect440x2Payload.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceListAllTogether() throws IOException {
        final String fileContent = Files.readAllLines(Paths.get("src/test/resources/deviceListAllTogetherPayload.xml"))
                .stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceList() throws IOException {
        final String fileContent = Files.readAllLines(Paths.get("src/test/resources/deviceList.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceListAllTogetherWithBlind() throws IOException {
        final String fileContent = Files
                .readAllLines(Paths.get("src/test/resources/FritzOS29/deviceListAllTogetherWithBlind.xml"))
                .stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceStatsFritzDect200() throws IOException {
        final String fileContent = Files
                .readAllLines(Paths.get("src/test/resources/FritzOS29/devicestatsFritzDect200.xml"))
                .stream()
                .collect(joining("\n"));
        final DeviceStats stats = new Deserializer().parse(fileContent, DeviceStats.class);
        assertEquals(1, stats.getTemperature().get().getStats().size(), "Temperature has just one statistics Element");
        assertEquals(Double.valueOf(0.1),
                stats.getTemperature().get().getStats().get(0).getMeasurementUnit().getPrescision(),
                "Temperature statistics have unit precision '0.1'");

        assertEquals(2, stats.getEnergy().get().getStats().size(), "Energy has two statistics Element");

        assertEquals(false, stats.getHumidity().isPresent(), "Humidity is missing");
    }

    @Test
    public void parseSessionInfo() throws IOException {
        final String fileContent = Files.readAllLines(Paths.get("src/test/resources/sessionInfo.xml")).stream()
                .collect(joining("\n"));
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

    public void parseDeviceGroup() {
        // given
        final Group group = deviceList6840.getGroupById("900");
        // then
        assertThat(group)
                .hasName("PV")
                .hasPresent("1");
    }

    @Test
    public void parseDeviceGroupSwitch() {
        // given
        final Group group = deviceList6840.getGroupById("900");

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
    public void parseDeviceGroupPowerMeter() {
        // given
        final Group group = deviceList6840.getGroupById("900");

        // when
        final PowerMeter powerMeter = group.getPowerMeter();

        // then
        assertThat(powerMeter)
                .hasEnergyWattHours(875112)
                .hasPowerMilliWatt(38480);

    }

    @Test
    public void parseDeviceGroupGroupInfo() {
        // given
        final Group group = deviceList6840.getGroupById("900");

        // when
        final GroupInfo groupInfo = group.getGroupInfo();

        // then
        assertThat(groupInfo)
                .hasMasterDeviceId("0")
                .containsAllMembers("16");

    }

}
