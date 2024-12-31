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

@Root(name = "temperature")
public class Temperature extends AbstractDeviceStatistics {

    @Element(name = "celsius", required = false)
    private int deciCelsius;

    @Element(name = "offset", required = false)
    private int offsetDeciCelsius;

    public float getCelsius() {
        return (deciCelsius + offsetDeciCelsius) / 10F;
    }

    @Override
    public String toString() {
        return "Temperature [celsius=" + getCelsius() + "]";
    }
}
