package com.deividsantos.assembly.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ErrorTranslator implements MessageSourceAware {

    private MessageSource messageSource;

    public Error translate(Error error) {
        if (error == null) {
            return null;
        }
        final String message = get(error.getMessage());
        final String code = get(error.getCode());
        return new Error(message, code);
    }

    private String get(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.ENGLISH);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}