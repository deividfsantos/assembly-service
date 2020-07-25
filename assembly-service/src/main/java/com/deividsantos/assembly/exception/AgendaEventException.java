package com.deividsantos.assembly.exception;

public class AgendaEventException extends RuntimeException {

    public AgendaEventException() {
        super("Error when processing mapping event message.");
    }
}
