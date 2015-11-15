package com.github.kaklakariada.fritzbox;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.fritzbox.http.HttpTemplate;
import com.github.kaklakariada.fritzbox.http.QueryParameters;
import com.github.kaklakariada.fritzbox.model.SessionInfo;

/**
 * This class implements allows logging in to a fritzbox and execute authenticated requests.
 */
public class FritzBoxSession {
    private final static Logger LOG = LoggerFactory.getLogger(FritzBoxSession.class);

    private static final String LOGIN_PATH = "/login_sid.lua";
    private static final String WEBCM_PATH = "/home/home.lua";
    private static final String EMPTY_SESSION_ID = "0000000000000000";

    private String sid;

    private final HttpTemplate httpTemplate;

    FritzBoxSession(HttpTemplate httpTemplate) {
        this.httpTemplate = httpTemplate;
    }

    public void login(String username, String password) {
        final SessionInfo sessionWithChallenge = httpTemplate.get(LOGIN_PATH, SessionInfo.class);
        if (!EMPTY_SESSION_ID.equals(sessionWithChallenge.getSid())) {
            throw new RuntimeException("Already logged in: " + sessionWithChallenge);
        }
        final String response = createChallengeResponse(sessionWithChallenge.getChallenge(), password);
        LOG.debug("Got response {} for challenge {}", response, sessionWithChallenge.getChallenge());

        final SessionInfo loggedInSession = httpTemplate.get(LOGIN_PATH,
                QueryParameters.builder().add("username", username).add("response", response).build(),
                SessionInfo.class);
        if (EMPTY_SESSION_ID.equals(loggedInSession.getSid())) {
            throw new RuntimeException("Login failed: " + loggedInSession);
        }
        LOG.debug("Logged in with session id {}", loggedInSession.getSid());
        this.sid = loggedInSession.getSid();
    }

    private static String createChallengeResponse(String challenge, String password) {
        try {
            final byte[] text = (challenge + "-" + password).getBytes(Charset.forName("utf-16le"));
            final byte[] digest = MessageDigest.getInstance("md5").digest(text);
            return challenge + "-" + DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getAutenticated(String path, QueryParameters parameters, Class<T> resultType) {
        if (sid == null) {
            throw new RuntimeException("Not logged in, session id is null");
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
