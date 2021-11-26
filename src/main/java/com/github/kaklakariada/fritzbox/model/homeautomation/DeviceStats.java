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

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "devicestats")
public class DeviceStats {

    @Element(name = "temperature", type = Temperature.class, required = false)
    private Temperature temperature;

    @Element(name = "voltage", type = Voltage.class, required = false)
    private Voltage voltage;

    @Element(name = "power", type = Power.class, required = false)
    private Power power;

    @Element(name = "energy", type = Energy.class, required = false)
    private Energy energy;

    @Element(name = "humidity", type = Humidity.class, required = false)
    private Humidity humidity;

    public Optional<AbstractDeviceStatistics> getTemperature() {
        return Optional.ofNullable(temperature);
    }
    public Optional<AbstractDeviceStatistics> getVoltage() {
        return Optional.ofNullable(voltage);
    }
    public Optional<AbstractDeviceStatistics> getPower() {
        return Optional.ofNullable(power);
    }
    public Optional<AbstractDeviceStatistics> getEnergy() {
        return Optional.ofNullable(energy);
    }
    public Optional<AbstractDeviceStatistics> getHumidity() {
        return Optional.ofNullable(humidity);
    }


    //        @Override
    //        public String toString() {
    //            final StringBuffer sb = new StringBuffer();
    //            sb.append(String.format("Temperature %s values",
    //                    temperature != null ? temperature.getStats().getCount() : "none"));
    //            sb.append(", ");
    //            sb.append(String.format("Voltage %s values", voltage != null ? voltage.getStats().getCount() : "none"));
    //            sb.append(", ");
    //            sb.append(String.format("Power %s values", power != null ? power.getStats().getCount() : "none"));
    //            sb.append(", ");
    //            sb.append(String.format("Energy %s values", energy != null ? energy.getStats().get(0).getCount() : "none"));
    //            sb.append(", ");
    //            sb.append(String.format("Humidity %s values", humidity != null ? humidity.getStats().getCount() : "none"));
    //            return "Basic Device Statistcs [" + sb.toString() + "]";
    //        }
}
