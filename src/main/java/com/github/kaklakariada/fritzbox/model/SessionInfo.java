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
package com.github.kaklakariada.fritzbox.model;

import java.util.List;

import org.simpleframework.xml.*;

@Root(name = "SessionInfo")
public class SessionInfo {

    @Element(name = "SID")
    private String sid;

    @Element(name = "Challenge")
    private String challenge;

    @Element(name = "BlockTime")
    private String blockTime;

    @ElementList(name = "Rights", inline = false, required = false)
    private List<UserRight> rights;

    @ElementList(name = "Users", inline = false, required = false)
    private List<User> users;

    public String getSid() {
        return sid;
    }

    public String getChallenge() {
        return challenge;
    }

    public String getBlockTime() {
        return blockTime;
    }

    public List<UserRight> getRights() {
        return rights;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "SessionInfo [sid=" + sid
                + ", challenge=" + challenge
                + ", blockTime=" + blockTime
                + ", rights=" + rights
                + "]";
    }
}
