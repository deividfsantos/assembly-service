package com.deividsantos.assembly.exception;

import org.springframework.http.HttpStatus;

public class SessionAlreadyOpenException extends HttpException {
    private static final String MESSAGE = "error.SessionAlreadyOpen";

    public SessionAlreadyOpenException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public Error getError() {
        return new Error(MESSAGE, "error.SessionAlreadyOpen.code");
    }
}