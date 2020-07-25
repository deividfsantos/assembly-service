package com.deividsantos.assembly.config;

import com.deividsantos.assembly.exception.Error;
import com.deividsantos.assembly.exception.HttpException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

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

    @ExceptionHandler
    public ResponseEntity<Error> handleException(MethodArgumentNotValidException e) {
        return Optional.ofNullable(e)
                .map(MethodArgumentNotValidException::getBindingResult)
                .map(Errors::getFieldError)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .map(message -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(message, "ASB-004")))
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}