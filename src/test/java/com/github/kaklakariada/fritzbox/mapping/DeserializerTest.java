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

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

import com.github.kaklakariada.fritzbox.model.SessionInfo;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;

public class DeserializerTest {

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
        new Deserializer().parse(fileContent, SessionInfo.class);
    }
}
