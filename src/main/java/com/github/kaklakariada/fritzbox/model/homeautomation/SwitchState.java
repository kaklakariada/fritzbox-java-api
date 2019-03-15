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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Root(name = "switch")
public class SwitchState {
    private static final Logger LOG = LoggerFactory.getLogger(SwitchState.class);

    public enum SwitchMode {
        AUTO, MANUAL
    }

    @Element(name = "state", required = false)
    private String state;

    @Element(name = "mode", required = false)
    private String mode;

    @Element(name = "lock", required = false)
    private String lock;

    @Element(name = "devicelock", required = false)
    private String devicelock;

    boolean isNull() {
        return state == null || mode == null || lock == null;
    }

    public boolean isOn() {
        return "1".equals(state);
    }

    public boolean isLocked() {
        return "1".equals(lock);
    }

    public boolean isDeviceLocked() {
        return "1".equals(devicelock);
    }

    public SwitchMode getMode() {
        if (mode == null) {
            LOG.warn("Switch mode is null for {}", this);
            return null;
        }
        switch (mode) {
        case "auto":
            return SwitchMode.AUTO;
        case "manuell":
            return SwitchMode.MANUAL;
        default:
            LOG.warn("Unknown value for switch mode: '{}' for {}", mode, this);
            return null;
        }
    }

    @Override
    public String toString() {
        return "SwitchState [state=" + state + ", mode=" + mode + ", lock=" + lock + "]";
    }
}
