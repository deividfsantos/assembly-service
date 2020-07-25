package com.deividsantos.assembly.exception;

import org.springframework.http.HttpStatus;

public abstract class HttpException extends RuntimeException {
    public HttpException(String message) {
        super(message);
    }

    public abstract Error getError();

    public abstract HttpStatus getStatus();
}
