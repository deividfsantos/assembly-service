package com.deividsantos.assembly.api.rest.v1.output;

import io.swagger.annotations.ApiModelProperty;

public class ResultsOutput {
    @ApiModelProperty(value = "Total votes on the agenda.")
    private Integer totalVotes;
    @ApiModelProperty(value = "Total of votes \"Sim\" on the agenda.")
    private Integer positiveVotes;
    @ApiModelProperty(value = "Total of votes \"Não\" on the agenda.")
    private Integer negativeVotes;

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Integer getPositiveVotes() {
        return positiveVotes;
    }

    public void setPositiveVotes(Integer positiveVotes) {
        this.positiveVotes = positiveVotes;
    }

    public Integer getNegativeVotes() {
        return negativeVotes;
    }

    public void setNegativeVotes(Integer negativeVotes) {
        this.negativeVotes = negativeVotes;
    }
}
