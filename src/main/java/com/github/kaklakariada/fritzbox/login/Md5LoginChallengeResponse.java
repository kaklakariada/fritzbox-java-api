/**
 * A Java API for managing FritzBox HomeAutomation
 * Copyright (C) 2017 Christoph Pirkl <christoph at users.sourceforge.net>
 * <br>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <br>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <br>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.kaklakariada.fritzbox.login;

class Md5LoginChallengeResponse implements ChallengeResponse {

    private final Md5Service md5Service;

    Md5LoginChallengeResponse(final Md5Service md5Service) {
        this.md5Service = md5Service;
    }

    @Override
    public String calculateResponse(final String challenge, final String password) {
        final String text = (challenge + "-" + password);
        return challenge + "-" + md5Service.md5(text);
    }
}
