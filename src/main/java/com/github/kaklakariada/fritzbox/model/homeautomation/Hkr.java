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
    private int tIst;

    @Element(name = "tsoll", required = false)
    private int tSoll;

    @Element(name = "absenk", required = false)
    private int tAbsenk;

    @Element(name = "komfort", required = false)
    private int komfort;

    @Element(name = "lock", required = false)
    private int lock;

    @Element(name = "devicelock", required = false)
    private int deviceLock;

    @Element(name = "errorcode", required = false)
    private int errorCode;

    @Element(name = "windowopenactiv", required = false)
    private boolean windowOpenActive;

    @Element(name = "windowopenactiveendtime", required = false)
    private int windowOpenActiveEndTime;

    @Element(name = "boostactive", required = false)
    private boolean boostActive;

    @Element(name = "boostactiveendtime", required = false)
    private int boostActiveEndTime;

    @Element(name = "batterylow", required = false)
    private boolean batteryLow;

    @Element(name = "battery", required = false)
    private int battery;

    @Element(name = "nextchange", required = false)
    private NextChange nextChange;

    @Element(name = "summeractive", required = false)
    private boolean summerActive;

    @Element(name = "holidayactive", required = false)
    private boolean holidayActive;

    @Element(name = "adaptiveHeatingActive", required = false)
    private boolean adaptiveHeatingActive;

    @Element(name = "adaptiveHeatingRunning", required = false)
    private boolean adaptiveHeatingRunning;

    public int getTIst() {
        return tIst;
    }

    public void setTIst(final int tIst) {
        this.tIst = tIst;
    }

    public int getTSoll() {
        return tSoll;
    }

    public void setTSoll(final int tSoll) {
        this.tSoll = tSoll;
    }

    public int getTAbsenk() {
        return tAbsenk;
    }

    public void setTAbsenk(final int tAbsenk) {
        this.tAbsenk = tAbsenk;
    }

    public int getKomfort() {
        return komfort;
    }

    public void setKomfort(final int komfort) {
        this.komfort = komfort;
    }

    public int getLock() {
        return lock;
    }

    public void setLock(final int lock) {
        this.lock = lock;
    }

    public int getDeviceLock() {
        return deviceLock;
    }

    public void setDeviceLock(final int deviceLock) {
        this.deviceLock = deviceLock;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isWindowOpenActive() {
        return windowOpenActive;
    }

    public void setWindowOpenActive(final boolean windowOpenActive) {
        this.windowOpenActive = windowOpenActive;
    }

    public int getWindowOpenActiveEndTime() {
        return windowOpenActiveEndTime;
    }

    public void setWindowOpenActiveEndTime(final int windowOpenActiveEndTime) {
        this.windowOpenActiveEndTime = windowOpenActiveEndTime;
    }

    public boolean isBoostActive() {
        return boostActive;
    }

    public void setBoostActive(final boolean boostActive) {
        this.boostActive = boostActive;
    }

    public int getBoostActiveEndTime() {
        return boostActiveEndTime;
    }

    public void setBoostActiveEndTime(final int boostActiveEndTime) {
        this.boostActiveEndTime = boostActiveEndTime;
    }

    public boolean isBatteryLow() {
        return batteryLow;
    }

    public void setBatteryLow(final boolean batteryLow) {
        this.batteryLow = batteryLow;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(final int battery) {
        this.battery = battery;
    }

    public NextChange getNextChange() {
        return nextChange;
    }

    public void setNextChange(final NextChange nextChange) {
        this.nextChange = nextChange;
    }

    public boolean isSummerActive() {
        return summerActive;
    }

    public void setSummerActive(final boolean summerActive) {
        this.summerActive = summerActive;
    }

    public boolean isHolidayActive() {
        return holidayActive;
    }

    public void setHolidayActive(final boolean holidayActive) {
        this.holidayActive = holidayActive;
    }

    public boolean isAdaptiveHeatingActive() {
        return adaptiveHeatingActive;
    }

    public void setAdaptiveHeatingActive(final boolean adaptiveHeatingActive) {
        this.adaptiveHeatingActive = adaptiveHeatingActive;
    }

    public boolean isAdaptiveHeatingRunning() {
        return adaptiveHeatingRunning;
    }

    public void setAdaptiveHeatingRunning(final boolean adaptiveHeatingRunning) {
        this.adaptiveHeatingRunning = adaptiveHeatingRunning;
    }

    @Override
    public String toString() {
        return "Hkr [tIst=" + tIst
                + ", tSoll=" + tSoll
                + ", tAbsenk=" + tAbsenk
                + ", komfort=" + komfort
                + ", lock=" + lock
                + ", deviceLock=" + deviceLock
                + ", errorCode=" + errorCode
                + ", batteryLow=" + batteryLow
                + ", nextChange=" + nextChange
                + "]";
    }
}
