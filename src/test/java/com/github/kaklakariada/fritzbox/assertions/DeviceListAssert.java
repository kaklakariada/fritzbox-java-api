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
package com.github.kaklakariada.fritzbox.assertions;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;

import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;

public class DeviceListAssert extends AbstractObjectAssert<DeviceListAssert, DeviceList> {

    private static final String ERROR_MESSAGE = "Expected %s to be <%s> but was <%s> (%s)";

    DeviceListAssert(DeviceList actual) {
        super(actual, DeviceListAssert.class);
    }

    public DeviceListAssert hasGroupsSize(int expected) {
        int actualGroupsSize = actual.getGroups().size();
        Assertions.assertThat(actualGroupsSize)
                .overridingErrorMessage(ERROR_MESSAGE, "groupSize", expected, actualGroupsSize, descriptionText())
                .isEqualTo(expected);
        return this;
    }
}
