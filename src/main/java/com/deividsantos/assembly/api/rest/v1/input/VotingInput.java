package com.deividsantos.assembly.api.rest.v1.input;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VotingInput {
    @ApiModelProperty(value = "Option of vote.")
    @NotBlank(message = "Option of vote is mandatory.")
    private String vote;
    @ApiModelProperty(value = "Associated informations.")
    @NotNull
    @Valid
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
