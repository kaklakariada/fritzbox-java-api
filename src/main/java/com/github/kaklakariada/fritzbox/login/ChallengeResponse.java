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

public interface ChallengeResponse {
    String calculateResponse(final String challenge, final String password);

    static ChallengeResponse getAlgorithm(final String challenge) {
        if (challenge.startsWith("2$")) {
            return new Pbkdf2ChallengeResponse();
        } else {
            return new Md5LoginChallengeResponse(new Md5Service());
        }
    }
}
