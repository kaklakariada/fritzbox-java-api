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

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.fritzbox.http.QueryParameters;
import com.github.kaklakariada.fritzbox.http.QueryParameters.Builder;
import com.github.kaklakariada.fritzbox.model.homeautomation.Device;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceStats;

public class HomeAutomation {

    private static final Logger LOG = LoggerFactory.getLogger(HomeAutomation.class);
    private static final String HOME_AUTOMATION_PATH = "/webservices/homeautoswitch.lua";

    private final FritzBoxSession session;

    private enum ParamName {
        PARAM("param"), LEVEL("level"), TARGET("target");

        private String paramName;

        ParamName(String paramName) {
            this.paramName = paramName;
        }
    }

    private HomeAutomation(FritzBoxSession fritzbox) {
        this.session = fritzbox;
    }

    public static HomeAutomation connect(String baseUrl, String username, String password) {
        final FritzBoxSession session = new FritzBoxSession(baseUrl);
        session.login(username, password);
        return new HomeAutomation(session);
    }

    public DeviceList getDeviceListInfos() {
        final DeviceList deviceList = executeCommand("getdevicelistinfos", DeviceList.class);
        LOG.trace("Found {} devices, devicelist version: {}", deviceList.getDevices().size(),
                deviceList.getApiVersion());
        return deviceList;
    }
    
    public Device getDeviceInfos(String deviceAin) {
        return executeDeviceCommand(deviceAin, "getdeviceinfos", null, Device.class);
    }

    private <T> T executeCommand(String command, Class<T> resultType) {
        final QueryParameters parameters = QueryParameters.builder().add("switchcmd", command).build();
        return session.getAutenticated(HOME_AUTOMATION_PATH, parameters, resultType);
    }

    private <T> T executeParamCommand(String deviceAin, String command, String parameter, Class<T> responseType) {
        return executeDeviceCommand(deviceAin, command, ParamName.PARAM, parameter, responseType);
    }

    private <T> T executeLevelCommand(String deviceAin, String command, String parameter, Class<T> responseType) {
        return executeDeviceCommand(deviceAin, command, ParamName.LEVEL, parameter, responseType);
    }

    private <T> T executeTargetCommand(String deviceAin, String command, String parameter, Class<T> responseType) {
        return executeDeviceCommand(deviceAin, command, ParamName.TARGET, parameter, responseType);
    }

    private <T> T executeDeviceCommand(String deviceAin, String command, ParamName paramName, String parameter,
            Class<T> responseType) {
        final Builder paramBuilder = QueryParameters.builder().add("ain", deviceAin).add("switchcmd", command);
        if (parameter != null) {
            paramBuilder.add(paramName.paramName, parameter);
        }
        return session.getAutenticated(HOME_AUTOMATION_PATH, paramBuilder.build(), responseType);
    }

    public List<String> getSwitchList() {
        final String switches = executeCommand("getswitchlist", String.class);
        final List<String> idList = Arrays.asList(switches.split(","));
        LOG.trace("Got switch list string '{}': {}", switches, idList);
        return idList;
    }

    public void switchPowerState(String deviceAin, boolean on) {
        final String command = on ? "setswitchon" : "setswitchoff";
        executeParamCommand(deviceAin, command, null, Boolean.class);
    }

    public void togglePowerState(String deviceAin) {
        executeParamCommand(deviceAin, "setswitchtoggle", null, Boolean.class);
    }

    public void setHkrTsoll(String deviceAin, String tsoll) {
        executeParamCommand(deviceAin, "sethkrtsoll", tsoll, Boolean.class);
    }

    public void setBlind(String deviceAin, String target) {
        executeTargetCommand(deviceAin, "setblind", target, Boolean.class);
    }

    public void setLevel(String deviceAin, String level) {
        executeLevelCommand(deviceAin, "setlevel", level, Boolean.class);
    }

    public void setLevelPercentage(String deviceAin, String level) {
        executeLevelCommand(deviceAin, "setlevelpercentage", level, Boolean.class);
    }

    public boolean getSwitchState(String deviceAin) {
        return executeParamCommand(deviceAin, "getswitchstate", null, Boolean.class);
    }

    public boolean getSwitchPresent(String deviceAin) {
        return executeParamCommand(deviceAin, "getswitchpresent", null, Boolean.class);
    }

    public String getSwitchName(String deviceAin) {
        return executeParamCommand(deviceAin, "getswitchname", null, String.class);
    }

    public Float getTemperature(String deviceAin) {
        final Integer centiDegree = executeParamCommand(deviceAin, "gettemperature", null, Integer.class);
        return centiDegree == null ? null : centiDegree / 10F;
    }

    public DeviceStats getBasicStatistics(String deviceAin) {
        return executeParamCommand(deviceAin, "getbasicdevicestats", null, DeviceStats.class);
    }

    public Float getSwitchPowerWatt(String deviceAin) {
        final Integer powerMilliWatt = executeParamCommand(deviceAin, "getswitchpower", null, Integer.class);
        return powerMilliWatt == null ? null : powerMilliWatt / 1000F;
    }

    public Integer getSwitchEnergyWattHour(String deviceAin) {
        return executeParamCommand(deviceAin, "getswitchenergy", null, Integer.class);
    }

    public EnergyStatisticsService getEnergyStatistics() {
        return new EnergyStatisticsService(session);
    }

    public void logout() {
        session.logout();
    }
}
