package com.deividsantos.assembly.config;

import com.deividsantos.assembly.exception.Error;
import com.deividsantos.assembly.exception.HttpException;
import com.deividsantos.assembly.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<Error> handleException(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException e = (HttpException) throwable;
            return ResponseEntity
                    .status(e.getStatus())
                    .body(e.getError());
        }
        return getInternalErrorResponse(throwable);
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleException(MethodArgumentNotValidException e) {
        return Optional.ofNullable(e)
                .map(MethodArgumentNotValidException::getBindingResult)
                .map(Errors::getFieldError)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .map(message -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(message, "ASB-004")))
                .orElse(getInternalErrorResponse(e));
    }

    private ResponseEntity<Error> getInternalErrorResponse(Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}