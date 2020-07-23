package com.deividsantos.assembly.rest.api.v1.output;

public class AgendaOutput {
    private Integer id;

    public AgendaOutput() {
    }

    public AgendaOutput(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
