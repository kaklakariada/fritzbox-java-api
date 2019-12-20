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
