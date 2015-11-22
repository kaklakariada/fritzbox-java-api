package com.github.kaklakariada.fritzbox.mapping;

public class DeserializerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DeserializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeserializerException(String message) {
        super(message);
    }
}
