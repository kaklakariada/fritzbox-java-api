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
package com.github.kaklakariada.fritzbox.model.homeautomation;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "devicelist")
public class DeviceList {

    @Attribute(name = "version")
    private String apiVersion;

    @ElementList(name = "device", type = Device.class, inline = true)
    private List<Device> devices;

    public String getApiVersion() {
        return apiVersion;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public Device getDeviceByIdentifier(String identifier) {
        return devices.stream() //
                .filter(d -> identifierMatches(d, identifier)) //
                .findFirst().orElse(null);
    }

    public List<String> getDeviceIdentifiers() {
        return devices.stream() //
                .map(Device::getIdentifier) //
                .collect(toList());
    }

    private static boolean identifierMatches(Device device, String identifier) {
        return normalizeIdentifier(device.getIdentifier()).equals(normalizeIdentifier(identifier));
    }

    private static String normalizeIdentifier(String identifier) {
        return identifier.replace(" ", "");
    }

    @Override
    public String toString() {
        return "DeviceList [apiVersion=" + apiVersion + ", devices=" + devices + "]";
    }
}
