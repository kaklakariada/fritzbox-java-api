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
package com.github.kaklakariada.fritzbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.fritzbox.http.HttpTemplate;
import com.github.kaklakariada.fritzbox.http.QueryParameters;
import com.github.kaklakariada.fritzbox.login.ChallengeResponse;
import com.github.kaklakariada.fritzbox.model.SessionInfo;

/**
 * This class allows logging in to a fritzbox and execute authenticated requests.
 */
public class FritzBoxSession {
    private static final Logger LOG = LoggerFactory.getLogger(FritzBoxSession.class);

    private static final String LOGIN_PATH = "/login_sid.lua";
    private static final String WEBCM_PATH = "/home/home.lua";
    private static final String EMPTY_SESSION_ID = "0000000000000000";

    private String sid;

    private final HttpTemplate httpTemplate;

    FritzBoxSession(final String baseUrl, final String certificatChecksum) {
        this(HttpTemplate.create(baseUrl, certificatChecksum));
    }

    FritzBoxSession(final HttpTemplate httpTemplate) {
        this(httpTemplate, null);
    }

    private FritzBoxSession(final HttpTemplate httpTemplate, final String sid) {
        this.httpTemplate = httpTemplate;
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }

    public void login(final String username, final String password) {
        final SessionInfo sessionWithChallenge = httpTemplate.get(LOGIN_PATH,
                QueryParameters.builder().add("version", "2").build(), SessionInfo.class);
        if (!EMPTY_SESSION_ID.equals(sessionWithChallenge.getSid())) {
            throw new FritzBoxException("Already logged in: " + sessionWithChallenge);
        }
        final String response = createResponse(sessionWithChallenge.getChallenge(), password);
        LOG.debug("Got response {} for challenge {}", response, sessionWithChallenge.getChallenge());

        final QueryParameters arguments = QueryParameters.builder() //
                .add("username", username == null ? "" : username) //
                .add("response", response) //
                .build();
        final SessionInfo sessionInfo = httpTemplate.get(LOGIN_PATH, arguments, SessionInfo.class);
        if (EMPTY_SESSION_ID.equals(sessionInfo.getSid())) {
            throw new LoginFailedException(sessionInfo);
        }
        LOG.debug("Logged in with session id {} and rights {}", sessionInfo.getSid(), sessionInfo.getRights());
        this.sid = sessionInfo.getSid();
    }

    private String createResponse(final String challenge, final String password) {
        return ChallengeResponse.getAlgorithm(challenge).calculateResponse(challenge, password);
    }

    public <T> T getAutenticated(final String path, final QueryParameters parameters, final Class<T> resultType) {
        if (sid == null) {
            throw new FritzBoxException("Not logged in, session id is null");
        }
        final QueryParameters parametersWithSessionId = parameters.newBuilder().add("sid", this.sid).build();
        return httpTemplate.get(path, parametersWithSessionId, resultType);
    }

    public void logout() {
        httpTemplate.get(WEBCM_PATH, QueryParameters.builder().add("sid", sid).add("logout", "1").build(),
                String.class);
        LOG.debug("Logged out, invalidate sid");
        sid = null;
    }
}
