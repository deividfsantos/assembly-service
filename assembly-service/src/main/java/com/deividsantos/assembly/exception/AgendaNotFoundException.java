package com.deividsantos.assembly.exception;

import org.springframework.http.HttpStatus;

public class AgendaNotFoundException extends HttpException {
    private static final String MESSAGE = "Agenda not found.";

    public AgendaNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public Error getError() {
        return new Error(MESSAGE, "ASB-006");
    }
}