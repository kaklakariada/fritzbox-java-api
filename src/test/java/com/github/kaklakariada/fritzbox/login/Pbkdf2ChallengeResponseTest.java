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
package com.github.kaklakariada.fritzbox.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class Pbkdf2ChallengeResponseTest {

    @Test
    void test() {
        assertEquals(
                "91a3f9eca12316c9461667af0fe36d2f$00e538d77b29ee7ea349a8604dc48570024d9084e5082ebe3d3540a441ea6108",
                calculate(
                        "2$60000$58e7348c30c8376903802c21f9730310$6000$91a3f9eca12316c9461667af0fe36d2f", "password"));
    }

    @Test
    void invalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> calculate("invalid", "password"));
    }

    private String calculate(final String challenge, final String password) {
        return new Pbkdf2ChallengeResponse().calculateResponse(challenge, password);
    }
}
