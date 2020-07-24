package com.deividsantos.assembly.config;

import com.deividsantos.assembly.exception.Error;
import com.deividsantos.assembly.exception.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Error> handleException(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException e = (HttpException) throwable;
            return ResponseEntity
                    .status(e.getStatus())
                    .body(e.getError());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}