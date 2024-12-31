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

@Root(name = "button")
public class Button {

    @Attribute(name = "identifier")
    private String identifier;
    @Attribute(name = "id")
    private String id;
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "lastpressedtimestamp", required = false)
    private String lastPressedTimestamp;

    public String getIdentifier() {
        return identifier;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastPressedTimestamp() {
        return lastPressedTimestamp;
    }
}
