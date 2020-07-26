package com.deividsantos.assembly.exception;

import org.springframework.http.HttpStatus;

public class AlreadyVotedException extends HttpException {
    private static final String MESSAGE = "error.alreadyVoted";

    public AlreadyVotedException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public Error getError() {
        return new Error(MESSAGE, "error.alreadyVoted.code");
    }
}
