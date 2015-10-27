package com.github.fritzbox;

import java.net.URI;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.fritzbox.model.SessionInfo;

class FritzBoxSession {
    private final static Logger LOG = LoggerFactory.getLogger(FritzBoxSession.class);

    private static final String LOGIN_PATH = "/login_sid.lua";
    private static final String WEBCM_PATH = "/home/home.lua";
    private static final String EMPTY_SESSION_ID = "0000000000000000";
    private final RestTemplate restTemplate;
    private final URI loginUri;
    private final URI webcmUri;
    private final String host;
    private String sid;

    FritzBoxSession(String host, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.host = host;
        loginUri = createUri(host, LOGIN_PATH);
        webcmUri = createUri(host, WEBCM_PATH);
    }

    private URI createUri(String host, String path) {
        return createUri(host, path, null);
    }

    private URI createUri(String host, String path, MultiValueMap<String, String> params) {
        final UriComponentsBuilder builder = UriComponentsBuilder.newInstance() //
                .scheme("https") //
                .host(host) //
                .path(path);
        if (params != null) {
            builder.queryParams(params);
        }
        return builder //
                .build().toUri();
    }

    public void login(String username, String password) {
        final SessionInfo sessionWithChallenge = restTemplate.getForObject(loginUri, SessionInfo.class);
        if (!EMPTY_SESSION_ID.equals(sessionWithChallenge.getSid())) {
            throw new RuntimeException("Already logged in: " + sessionWithChallenge);
        }
        final String response = createChallengeResponse(sessionWithChallenge.getChallenge(), password);
        LOG.debug("Got response {} for challenge {}", response, sessionWithChallenge.getChallenge());
        final SessionInfo loggedInSession = sendResponse(username, response);
        if (EMPTY_SESSION_ID.equals(loggedInSession.getSid())) {
            throw new RuntimeException("Login failed: " + loggedInSession);
        }
        LOG.debug("Got sid {}", loggedInSession.getSid());
        this.sid = loggedInSession.getSid();
    }

    public void logout() {
        final MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
        request.add("sid", sid);
        request.add("logout=", "1");
        final String string = restTemplate.postForObject(webcmUri, request, String.class);
        LOG.debug("Logout successful: {}", string);
    }

    public <T> T getForObject(String path, Map<String, String> args, Class<T> responseType) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.setAll(args);
        params.set("sid", sid);
        return restTemplate.getForObject(createUri(host, path, params), responseType);
    }

    private SessionInfo sendResponse(String username, final String response) {
        final MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
        request.add("username", username);
        request.add("response", response);
        return restTemplate.postForObject(loginUri, request, SessionInfo.class);
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
}
