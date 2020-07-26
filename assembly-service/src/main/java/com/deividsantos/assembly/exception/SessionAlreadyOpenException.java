package com.deividsantos.assembly.exception;

import org.springframework.http.HttpStatus;

public class SessionAlreadyOpenException extends HttpException {
    private static final String MESSAGE = "There is already an open session for this agenda.";

    public SessionAlreadyOpenException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public Error getError() {
        return new Error(MESSAGE, "ASB-007");
    }
}