package com.deividsantos.assembly.exception;

import org.springframework.http.HttpStatus;

public class IncorrectCpfFormatException extends HttpException {
    private static final String MESSAGE = "error.incorrectCpf";

    public IncorrectCpfFormatException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public Error getError() {
        return new Error(MESSAGE, "error.incorrectCpf.code");
    }
}