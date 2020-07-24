package com.deividsantos.assembly.exception;

import org.springframework.http.HttpStatus;

public class UnableToVoteException extends HttpException {
    private static final String MESSAGE = "This CPF is unable to vote.";

    public UnableToVoteException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public Error getError() {
        return new Error(MESSAGE, "ASB-003");
    }
}
