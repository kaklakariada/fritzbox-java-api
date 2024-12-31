/**
 * A Java API for managing FritzBox HomeAutomation
 * Copyright (C) 2017 Christoph Pirkl <christoph at users.sourceforge.net>
 * <br>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <br>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <br>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.kaklakariada.fritzbox.model.homeautomation;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "powermeter", strict = false)
public class PowerMeter {

    @Element(name = "voltage", required = false)
    private int voltageMilliVolt;
    @Element(name = "power", required = false)
    private int powerMilliWatt;
    @Element(name = "energy", required = false)
    private int energyWattHours;

    public float getVoltageVolt() {
        return voltageMilliVolt / 1000F;
    }

    public float getPowerWatt() {
        return powerMilliWatt / 1000F;
    }

    public int getEnergyWattHours() {
        return energyWattHours;
    }

    public int getPowerMilliWatt() {
        return powerMilliWatt;
    }

    @Override
    public String toString() {
        return "PowerMeter [voltage=" + getVoltageVolt()
                + ", energyWattHours=" + energyWattHours
                + ", powerWatt=" + getPowerWatt()
                + "]";
    }
}
