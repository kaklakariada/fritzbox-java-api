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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.kaklakariada.fritzbox.model.SessionInfo;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;
import com.github.kaklakariada.fritzbox.model.homeautomation.Group;
import com.github.kaklakariada.fritzbox.model.homeautomation.GroupInfo;
import com.github.kaklakariada.fritzbox.model.homeautomation.PowerMeter;
import com.github.kaklakariada.fritzbox.model.homeautomation.SwitchState;

public class DeserializerTest {

    private static DeviceList deviceList6840 = null;

    @BeforeClass
    public static void setupDeviceList6840() throws IOException {
        final String fileContent6840 = String.join("\n", Files.readAllLines(Paths.get("src/test/resources/devicelist6840.xml")));
        deviceList6840 = new Deserializer().parse(fileContent6840, DeviceList.class);
        assertThat(deviceList6840).hasGroupsSize(2);
    }

    @Test
    public void parseDeviceListFritzDect200() throws IOException {
        final String fileContent = Files.readAllLines(Paths.get("src/test/resources/deviceListConnectedFritzDect200Payload.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceListFritzDect301() throws IOException {
        final String fileContent = Files.readAllLines(Paths.get("src/test/resources/deviceListConnectedFritzDect200Payload.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceListNotConnectedFritzDect500() throws IOException {
        final String fileContent = Files.readAllLines(Paths.get("src/test/resources/deviceListNotConnectedFritzDect500Payload.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceListConnectedFritzDect500() throws IOException {
        final String fileContent = Files.readAllLines(Paths.get("src/test/resources/deviceListConnectedFritzDect500Payload.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceListConnectedFritzDect440() throws IOException {
        final String fileContent = Files.readAllLines(Paths.get("src/test/resources/deviceListConnectedFritzDect440Payload.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceListConnectedFritzDect440x2() throws IOException {
        final String fileContent = Files.readAllLines(Paths.get("src/test/resources/deviceListConnectedFritzDect440x2Payload.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }

    @Test
    public void parseDeviceListAllTogether() throws IOException {
        final String fileContent = Files.readAllLines(Paths.get("src/test/resources/deviceListAllTogetherPayload.xml")).stream()
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
    public void parseSessionInfo() throws IOException {
        final String fileContent = Files.readAllLines(Paths.get("src/test/resources/sessionInfo.xml")).stream()
                .collect(joining("\n"));
        SessionInfo sessionInfo = new Deserializer().parse(fileContent, SessionInfo.class);
        TestCase.assertNotNull(sessionInfo.getUsers());
        TestCase.assertEquals(3, sessionInfo.getUsers().size());
        TestCase.assertEquals("UserA", sessionInfo.getUsers().get(0).getName());
        TestCase.assertFalse(sessionInfo.getUsers().get(0).isLast());
        TestCase.assertEquals("UserB", sessionInfo.getUsers().get(1).getName());
        TestCase.assertFalse(sessionInfo.getUsers().get(1).isLast());
        TestCase.assertEquals("UserC", sessionInfo.getUsers().get(2).getName());
        TestCase.assertTrue(sessionInfo.getUsers().get(2).isLast());
    }
    public void parseDeviceGroup() {
        //given
        Group group = deviceList6840.getGroupById("900");
        //then
        assertThat(group)
                .hasName("PV")
                .hasPresent("1");
    }

    @Test
    public void parseDeviceGroupSwitch() {
        //given
        Group group = deviceList6840.getGroupById("900");

        //when
        SwitchState switchState = group.getSwitchState();

        //then
        assertThat(switchState)
                .isOn(true)
                .isDeviceLocked(false)
                .isLocked(false)
                .hasMode(SwitchState.SwitchMode.MANUAL);

    }

    @Test
    public void parseDeviceGroupPowerMeter() {
        //given
        Group group = deviceList6840.getGroupById("900");

        //when
        PowerMeter powerMeter = group.getPowerMeter();

        //then
        assertThat(powerMeter)
                .hasEnergyWattHours(875112)
                .hasPowerMilliWatt(38480);

    }

    @Test
    public void parseDeviceGroupGroupInfo() {
        //given
        Group group = deviceList6840.getGroupById("900");

        //when
        GroupInfo groupInfo = group.getGroupInfo();

        //then
        assertThat(groupInfo)
                .hasMasterDeviceId("0")
                .containsAllMembers("16");

    }

}
