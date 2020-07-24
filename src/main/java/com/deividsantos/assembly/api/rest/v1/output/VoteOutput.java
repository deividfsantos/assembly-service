package com.deividsantos.assembly.api.rest.v1.output;

import io.swagger.annotations.ApiModelProperty;

public class VoteOutput {
    @ApiModelProperty(value = "Vote id.")
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
