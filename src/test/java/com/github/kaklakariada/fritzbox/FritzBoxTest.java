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

import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class FritzBoxTest {

    private static final String PASSWORD = "";
    public static final String HTTP_FRITZ_BOX = "http://fritz.box";
    private static FritzBoxSession fritzBoxSession;

    @Before
    public void setUp() {
        fritzBoxSession = new FritzBoxSession(HTTP_FRITZ_BOX);
        fritzBoxSession.login("", PASSWORD);
    }

    @Test
    public void getSessionIdTest() {
        String sid = fritzBoxSession.getSid();
        Assert.assertNotNull(sid);
    }

    @Test
    public void readAllDevicesTest() {
        HomeAutomation homeAutomation = HomeAutomation.connect(HTTP_FRITZ_BOX, "", PASSWORD);
        DeviceList deviceListInfos = homeAutomation.getDeviceListInfos();
        deviceListInfos.getDevices().stream().forEach(t -> {
            System.out.println("Identifier: " + t.getIdentifier() + " Name: " + t.getName() + " Temperature: " + t.getTemperature().getCelsius());
        });
    }

    @Test
    public void readDevicesByIdentifierTest() {
        HomeAutomation homeAutomation = HomeAutomation.connect(HTTP_FRITZ_BOX, "", PASSWORD);
        System.out.println("Temperature: "+homeAutomation.getTemperature("11657 0071130"));
    }
}
