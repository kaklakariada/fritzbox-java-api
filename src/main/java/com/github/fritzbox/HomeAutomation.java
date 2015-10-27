package com.github.fritzbox;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fritzbox.model.homeautomation.DeviceList;

public class HomeAutomation {

    private final static Logger LOG = LoggerFactory.getLogger(HomeAutomation.class);
    private final static String HOME_AUTOMATION_PATH = "/webservices/homeautoswitch.lua";

    private final FritzBoxSession fritzbox;

    public HomeAutomation(FritzBoxSession fritzbox) {
        this.fritzbox = fritzbox;
    }

    public DeviceList getDeviceListInfos() {
        return fritzbox.getForObject(HOME_AUTOMATION_PATH, Collections.singletonMap("switchcmd", "getdevicelistinfos"),
                DeviceList.class);
    }

}
