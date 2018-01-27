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

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "hkr")
public class Hkr {

    @Element(name = "tist", required = false)
    private int tist;

    @Element(name = "tsoll", required = false)
    private int tsoll;

    @Element(name = "absenk", required = false)
    private int tabsenk;

    @Element(name = "komfort", required = false)
    private int komfort;

    @Element(name = "lock", required = false)
    private int lock;

    @Element(name = "devicelock", required = false)
    private int devicelock;

    @Element(name = "errorcode", required = false)
    private int errorcode;

    @Element(name = "batterylow", required = false)
    private int batterylow;

    @Element(name = "nextchange", required = false)
    private NextChange nextChange;

    public int getTist() {
        return tist;
    }

    public void setTist(int tist) {
        this.tist = tist;
    }

    public int getTsoll() {
        return tsoll;
    }

    public void setTsoll(int tsoll) {
        this.tsoll = tsoll;
    }

    public int getTabsenk() {
        return tabsenk;
    }

    public void setTabsenk(int tabsenk) {
        this.tabsenk = tabsenk;
    }

    public int getKomfort() {
        return komfort;
    }

    public void setKomfort(int komfort) {
        this.komfort = komfort;
    }

    public int getLock() {
        return lock;
    }

    public void setLock(int lock) {
        this.lock = lock;
    }

    public int getDevicelock() {
        return devicelock;
    }

    public void setDevicelock(int devicelock) {
        this.devicelock = devicelock;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public int getBatterylow() {
        return batterylow;
    }

    public void setBatterylow(int batterylow) {
        this.batterylow = batterylow;
    }

    public NextChange getNextChange() {
        return nextChange;
    }

    public void setNextChange(NextChange nextChange) {
        this.nextChange = nextChange;
    }

    @Override
    public String toString() {
        return "Hkr [tist=" + tist + ", tsoll=" + tsoll + ", tabsenk=" + tabsenk + ", komfort=" + komfort + ", lock="
                + lock + ", devicelock=" + devicelock + ", errorcode=" + errorcode + ", batterylow=" + batterylow
                + ", nextChange=" + nextChange + "]";
    }
}
