package com.deividsantos.assembly.api.rest.v1.output;

import io.swagger.annotations.ApiModelProperty;

public class VoteOutput {
    @ApiModelProperty(value = "Vote id.")
    private Integer receipt;

    public VoteOutput() {
    }

    public VoteOutput(Integer receipt) {
        this.receipt = receipt;
    }

    public Integer getReceipt() {
        return receipt;
    }

    public void setReceipt(Integer receipt) {
        this.receipt = receipt;
    }
}
