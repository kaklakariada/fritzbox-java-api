package com.github.fritzbox;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        final String command = "getdevicelistinfos";
        final DeviceList deviceList = executeCommand(command, DeviceList.class);
        LOG.trace("Found {} devices, devicelist version: {}", deviceList.getDevices().size(),
                deviceList.getApiVersion());
        return deviceList;
    }

    private <T> T executeCommand(String command, Class<T> responseType) {
        final Map<String, String> args = Collections.singletonMap("switchcmd", command);
        // return fritzbox.getForObject(HOME_AUTOMATION_PATH, args, responseType);
        return null;
    }

    private String executeDeviceCommand(String deviceAin, String command, String parameter) {
        final Map<String, String> args = new HashMap<>();
        args.put("ain", deviceAin);
        args.put("switchcmd", command);
        if (parameter != null) {
            args.put("param", parameter);
        }
        return null;
    }

    public List<String> getSwitchList() {
        final String switches = executeCommand("getswitchlist", String.class);
        final List<String> idList = Arrays.stream(switches.split(",")) //
                .map(String::trim) //
                .collect(Collectors.toList());
        LOG.trace("Got switch list string '{}': {}", switches, idList);
        return idList;
    }

    public void switchPowerState(String deviceAin, boolean on) {
        final String command = on ? "setswitchon" : "setswitchoff";
        executeDeviceCommand(deviceAin, command, null);
    }

    public void togglePowerState(String deviceAin) {
        executeDeviceCommand(deviceAin, "setswitchtoggle", null);
    }

    public String getSwitchState(String deviceAin) {
        return executeDeviceCommand(deviceAin, "getswitchstate", null);
    }

    public String getSwitchPresent(String deviceAin) {
        return executeDeviceCommand(deviceAin, "getswitchpresent", null);
    }

    public String getSwitchName(String deviceAin) {
        return executeDeviceCommand(deviceAin, "getswitchname", null);
    }

    public float getTemperature(String deviceAin) {
        final String string = executeDeviceCommand(deviceAin, "gettemperature", null);
        final int centiDegree = Integer.parseInt(string);
        return centiDegree / 10F;
    }

    public float getSwitchPowerWatt(String deviceAin) {
        final String string = executeDeviceCommand(deviceAin, "getswitchpower", null);
        final int powerMilliWatt = Integer.parseInt(string);
        return powerMilliWatt / 1000F;
    }

    public int getSwitchEnergyWattHour(String deviceAin) {
        final String string = executeDeviceCommand(deviceAin, "getswitchenergy", null);
        final int energyWattHours = Integer.parseInt(string);
        return energyWattHours;
    }
}
