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

import java.util.Arrays;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="groupinfo")
public class GroupInfo {

    @Element(name="masterdeviceid")
    private String masterDeviceId;

    /**
     * Comma seperated list of devices identfied by their id.
     */
    @Element(name="members")
    private String members;

    public String getMasterDeviceId() {
        return masterDeviceId;
    }

    public String getMembers() {
        return members;
    }

    public List<String> getMemberList() {
        return Arrays.asList(this.members.split(","));
    }
}
