package com.deividsantos.assembly.api.rest.v1.input;

import io.swagger.annotations.ApiModelProperty;

public class VotingInput {
    @ApiModelProperty(value = "Option of vote.")
    private String vote;
    @ApiModelProperty(value = "Associated informations.")
    private AssociatedInput associated;

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public AssociatedInput getAssociated() {
        return associated;
    }

    public void setAssociated(AssociatedInput associated) {
        this.associated = associated;
    }
}
