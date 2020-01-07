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

import java.util.Optional;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "device")
public class Device {

    @Attribute(name = "identifier", required = true)
    private String identifier;
    @Attribute(name = "id")
    private String id;
    @Attribute(name = "functionbitmask")
    private int functionBitmask;
    @Attribute(name = "fwversion")
    private String firmwareVersion;
    @Attribute(name = "manufacturer")
    private String manufacturer;

    @Attribute(name = "productname")
    private String productName;

    @Element(name = "present")
    private String present;
    @Element(name = "name")
    private String name;

    @Element(name = "batterylow", required = false)
    private Integer batterylow;
    @Element(name = "battery", required = false)
    private Integer battery;
    @Element(name = "switch", required = false)
    private SwitchState switchState;
    @Element(name = "powermeter", required = false)
    private PowerMeter powerMeter;
    @Element(name = "temperature", required = false)
    private Temperature temperature;
    @Element(name = "hkr", required = false)
    private Hkr hkr;

    public String getIdentifier() {
        return identifier;
    }

    public String getId() {
        return id;
    }

    public int getFunctionBitmask() {
        return functionBitmask;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getProductName() {
        return productName;
    }

    public boolean isPresent() {
        return "1".equals(present);
    }

    public String getName() {
        return name;
    }

    public Optional<Boolean> isBatterylow() {
        return Optional.ofNullable(batterylow).map(value -> 1 == value);
    }

    public Optional<Integer> getBattery() {
        return Optional.ofNullable(battery);
    }

    public SwitchState getSwitchState() {
        return switchState.isNull() ? null : switchState;
    }

    public PowerMeter getPowerMeter() {
        return powerMeter;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Hkr getHkr() {
        return hkr;
    }

    @Override
    public String toString() {
        return "Device [identifier=" + identifier + ", id=" + id + ", functionBitmask=" + functionBitmask
                + ", firmwareVersion=" + firmwareVersion + ", manufacturer=" + manufacturer + ", productName="
                + productName + ", present=" + present + ", name=" + name + ", switchState=" + switchState
                + ", powerMeter=" + powerMeter + ", temperature=" + temperature + ", hkr=" + hkr + "]";
    }
}
