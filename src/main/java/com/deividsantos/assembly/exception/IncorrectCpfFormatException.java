package com.deividsantos.assembly.exception;

import org.springframework.http.HttpStatus;

public class IncorrectCpfFormatException extends HttpException {
    private static final String MESSAGE = "Incorrect CPF format;";

    public IncorrectCpfFormatException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public Error getError() {
        return new Error(MESSAGE, "ASB-005");
    }
}