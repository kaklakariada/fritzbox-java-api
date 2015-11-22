package com.github.kaklakariada.fritzbox.http;

public class AccessForbiddenException extends HttpException {

    private static final long serialVersionUID = 1L;

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(String message) {
        super(message);
    }
}
