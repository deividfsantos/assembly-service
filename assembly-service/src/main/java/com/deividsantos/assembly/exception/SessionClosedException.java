package com.deividsantos.assembly.exception;

import org.springframework.http.HttpStatus;

public class SessionClosedException extends HttpException {
    private static final String MESSAGE = "It is not possible to vote, no session open for this agenda.";

    public SessionClosedException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public Error getError() {
        return new Error(MESSAGE, "ASB-002");
    }
}
