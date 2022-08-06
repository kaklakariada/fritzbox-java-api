package com.github.kaklakariada.fritzbox.login;

import com.github.kaklakariada.fritzbox.Md5Service;

public class Md5Login implements ChallengeResponse {

    private final Md5Service md5Service;

    public Md5Login(final Md5Service md5Service) {
        this.md5Service = md5Service;
    }

    @Override
    public String calculateResponse(final String challenge, final String password) {
        final String text = (challenge + "-" + password);
        return challenge + "-" + md5Service.md5(text);
    }
}
