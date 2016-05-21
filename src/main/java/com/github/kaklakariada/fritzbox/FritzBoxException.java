package com.github.kaklakariada.fritzbox;

public class FritzBoxException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FritzBoxException(String message, Throwable cause) {
        super(message, cause);
    }

    public FritzBoxException(String message) {
        super(message);
    }
}
