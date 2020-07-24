package com.deividsantos.assembly.exception;

import org.springframework.http.HttpStatus;

public class AlreadyVotedException extends HttpException {
    private static final String MESSAGE = "You've already voted on this agenda.";

    public AlreadyVotedException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public Error getError() {
        return new Error(MESSAGE, "ASB-001");
    }
}
