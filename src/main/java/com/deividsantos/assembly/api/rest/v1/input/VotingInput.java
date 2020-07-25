package com.deividsantos.assembly.api.rest.v1.input;

import com.deividsantos.assembly.type.VoteOption;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class VotingInput {
    @ApiModelProperty(value = "Option of vote.")
    @NotNull(message = "Option of vote is mandatory.")
    private VoteOption vote;

    @ApiModelProperty(value = "Associated informations.")
    @NotNull
    @Valid
    private AssociatedInput associated;

    public VoteOption getVote() {
        return vote;
    }

    public void setVote(VoteOption vote) {
        this.vote = vote;
    }

    public AssociatedInput getAssociated() {
        return associated;
    }

    public void setAssociated(AssociatedInput associated) {
        this.associated = associated;
    }
}
