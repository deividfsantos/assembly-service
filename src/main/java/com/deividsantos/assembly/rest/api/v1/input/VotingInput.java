package com.deividsantos.assembly.rest.api.v1.input;

public class VotingInput {
    private String vote;
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
