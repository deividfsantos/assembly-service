package com.deividsantos.assembly.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class ErrorTranslator implements MessageSourceAware {

    private MessageSource messageSource;

    public Error translate(Error error) {
        return Optional.ofNullable(error)
                .map(errorNotNull -> new Error(get(errorNotNull.getMessage()), get(errorNotNull.getCode())))
                .orElse(null);
    }

    private String get(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.ENGLISH);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}