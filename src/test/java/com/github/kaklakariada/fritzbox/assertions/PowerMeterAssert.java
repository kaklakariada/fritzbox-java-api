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

import com.github.kaklakariada.fritzbox.model.homeautomation.PowerMeter;


public class PowerMeterAssert extends AbstractObjectAssert<PowerMeterAssert, PowerMeter> {

    private static final String ERROR_MESSAGE = "Expected %s to be <%s> but was <%s> (%s)";

    PowerMeterAssert(PowerMeter actual) {
        super(actual, PowerMeterAssert.class);
    }

    public PowerMeterAssert hasPowerMilliWatt(int expected) {
        int actual = this.actual.getPowerMilliWatt();
        Assertions.assertThat(actual)
                .overridingErrorMessage(ERROR_MESSAGE, "powerMilliWatt", expected, actual, descriptionText())
                .isEqualTo(expected);
        return this;
    }

    public PowerMeterAssert hasEnergyWattHours(int expected) {
        int actual = this.actual.getEnergyWattHours();
        Assertions.assertThat(actual)
                .overridingErrorMessage(ERROR_MESSAGE, "energyWattHours", expected, actual, descriptionText())
                .isEqualTo(expected);
        return this;
    }
}
