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

    private enum Param {
        PARAM("param"), LEVEL("level"), TARGET("target");

        private final String name;

        Param(final String name) {
            this.name = name;
        }
    }

    private HomeAutomation(final FritzBoxSession fritzbox) {
        this.session = fritzbox;
    }

    public static HomeAutomation connect(final Config config) {
        return connect(config.getUrl(), config.getCertificateChecksum().orElse(null), config.getUsername(),
                config.getPassword());
    }

    @Deprecated
    public static HomeAutomation connect(final String baseUrl, final String username, final String password) {
        return connect(baseUrl, null, username, password);
    }

    public static HomeAutomation connect(final String baseUrl, final String certificatChecksum, final String username,
            final String password) {
        LOG.info("Logging in to '{}' with username '{}'", baseUrl, username);
        final FritzBoxSession session = new FritzBoxSession(baseUrl, certificatChecksum);
        session.login(username, password);
        return new HomeAutomation(session);
    }

    public DeviceList getDeviceListInfos() {
        final DeviceList deviceList = executeCommand("getdevicelistinfos", DeviceList.class);
        LOG.trace("Found {} devices, devicelist version: {}", deviceList.getDevices().size(),
                deviceList.getApiVersion());
        return deviceList;
    }

    public Device getDeviceInfos(final String deviceAin) {
        return executeDeviceCommand(deviceAin, "getdeviceinfos", Param.PARAM, null, Device.class);
    }

    private <T> T executeCommand(final String command, final Class<T> resultType) {
        final QueryParameters parameters = QueryParameters.builder().add("switchcmd", command).build();
        return session.getAutenticated(HOME_AUTOMATION_PATH, parameters, resultType);
    }

    private <T> T executeParamCommand(final String deviceAin, final String command, final String parameter,
            final Class<T> responseType) {
        return executeDeviceCommand(deviceAin, command, Param.PARAM, parameter, responseType);
    }

    private <T> T executeLevelCommand(final String deviceAin, final String command, final String parameter,
            final Class<T> responseType) {
        return executeDeviceCommand(deviceAin, command, Param.LEVEL, parameter, responseType);
    }

    private <T> T executeTargetCommand(final String deviceAin, final String command, final String parameter,
            final Class<T> responseType) {
        return executeDeviceCommand(deviceAin, command, Param.TARGET, parameter, responseType);
    }

    private <T> T executeDeviceCommand(final String deviceAin, final String command, final Param paramName,
            final String parameter,
            final Class<T> responseType) {
        final Builder paramBuilder = QueryParameters.builder().add("ain", deviceAin).add("switchcmd", command);
        if (parameter != null) {
            paramBuilder.add(paramName.name, parameter);
        }
        return session.getAutenticated(HOME_AUTOMATION_PATH, paramBuilder.build(), responseType);
    }

    public List<String> getSwitchList() {
        final String switches = executeCommand("getswitchlist", String.class);
        final List<String> idList = Arrays.asList(switches.split(","));
        LOG.trace("Got switch list string '{}': {}", switches, idList);
        return idList;
    }

    public void switchPowerState(final String deviceAin, final boolean on) {
        final String command = on ? "setswitchon" : "setswitchoff";
        executeParamCommand(deviceAin, command, null, Boolean.class);
    }

    public void togglePowerState(final String deviceAin) {
        executeParamCommand(deviceAin, "setswitchtoggle", null, Boolean.class);
    }

    public void setHkrTsoll(final String deviceAin, final String tsoll) {
        executeParamCommand(deviceAin, "sethkrtsoll", tsoll, Boolean.class);
    }

    public void setBlind(final String deviceAin, final String target) {
        executeTargetCommand(deviceAin, "setblind", target, Boolean.class);
    }

    public void setLevel(final String deviceAin, final String level) {
        executeLevelCommand(deviceAin, "setlevel", level, Boolean.class);
    }

    public void setLevelPercentage(final String deviceAin, final String level) {
        executeLevelCommand(deviceAin, "setlevelpercentage", level, Boolean.class);
    }

    public boolean getSwitchState(final String deviceAin) {
        return executeParamCommand(deviceAin, "getswitchstate", null, Boolean.class);
    }

    public boolean getSwitchPresent(final String deviceAin) {
        return executeParamCommand(deviceAin, "getswitchpresent", null, Boolean.class);
    }

    public String getSwitchName(final String deviceAin) {
        return executeParamCommand(deviceAin, "getswitchname", null, String.class);
    }

    public Float getTemperature(final String deviceAin) {
        final Integer centiDegree = executeParamCommand(deviceAin, "gettemperature", null, Integer.class);
        return centiDegree == null ? null : centiDegree / 10F;
    }

    public DeviceStats getBasicStatistics(final String deviceAin) {
        return executeParamCommand(deviceAin, "getbasicdevicestats", null, DeviceStats.class);
    }

    public Float getSwitchPowerWatt(final String deviceAin) {
        final Integer powerMilliWatt = executeParamCommand(deviceAin, "getswitchpower", null, Integer.class);
        return powerMilliWatt == null ? null : powerMilliWatt / 1000F;
    }

    public Integer getSwitchEnergyWattHour(final String deviceAin) {
        return executeParamCommand(deviceAin, "getswitchenergy", null, Integer.class);
    }

    public EnergyStatisticsService getEnergyStatistics() {
        return new EnergyStatisticsService(session);
    }

    public void logout() {
        session.logout();
    }
}
