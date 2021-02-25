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

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "colorcontrol")
public class ColorControl {
    @Element(name = "hue", required = false)
    private String hue;

    @Element(name = "saturation", required =  false)
    private String saturation;

    @Element(name = "temperature", required = false)
    private String temperature;

    @Attribute(name = "supported_modes")
    private String supportedModes;

    @Attribute(name = "current_mode", required = false)
    private String current_mode;

    @Attribute(name = "fullcolorsupport", required = false)
    private String fullcolorsupport;

    @Attribute(name = "mapped", required = false)
    private String mapped;

    @Element(name = "unmapped_hue", required = false)
    private String unmapped_hue;

    @Element(name = "unmapped_saturation", required = false)
    private String unmapped_saturation;

    public String getHue() {
        return hue;
    }

    public String getSaturation() {
        return saturation;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getSupportedModes() {
        return supportedModes;
    }

    public String getCurrent_mode() {
        return current_mode;
    }

    public String getFullcolorsupport() {
        return fullcolorsupport;
    }

    public String getMapped() {
        return mapped;
    }

    public String getUnmapped_hue() {
        return unmapped_hue;
    }

    public String getUnmapped_saturation() {
        return unmapped_saturation;
    }
}
