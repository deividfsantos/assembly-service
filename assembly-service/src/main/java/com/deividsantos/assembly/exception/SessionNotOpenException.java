package com.deividsantos.assembly.exception;

import org.springframework.http.HttpStatus;

public class SessionNotOpenException extends HttpException {
    private static final String MESSAGE = "error.SessionNotOpen";

    public SessionNotOpenException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public Error getError() {
        return new Error(MESSAGE, "error.SessionNotOpen.code");
    }
}
