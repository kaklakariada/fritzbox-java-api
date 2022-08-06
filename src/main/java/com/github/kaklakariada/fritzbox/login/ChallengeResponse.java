package com.github.kaklakariada.fritzbox.login;

import com.github.kaklakariada.fritzbox.Md5Service;

public interface ChallengeResponse {
    String calculateResponse(final String challenge, final String password);

    static ChallengeResponse getAlgorithm(final String challenge) {
        if (challenge.startsWith("2$")) {
            return new Pbkdf2Login();
        } else {
            return new Md5Login(new Md5Service());
        }
    }
}
