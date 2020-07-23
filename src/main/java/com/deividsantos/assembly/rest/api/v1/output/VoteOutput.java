package com.deividsantos.assembly.rest.api.v1.output;

public class VoteOutput {
    private Integer id;

    public VoteOutput() {
    }

    public VoteOutput(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
