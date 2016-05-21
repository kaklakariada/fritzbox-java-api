package com.github.kaklakariada.fritzbox;

import com.github.kaklakariada.fritzbox.model.SessionInfo;

public class LoginFailedException extends FritzBoxException {

    private static final long serialVersionUID = 1L;

    public LoginFailedException(SessionInfo session) {
        super("Login failed, blocked for " + session.getBlockTime() + " min");
    }
}
