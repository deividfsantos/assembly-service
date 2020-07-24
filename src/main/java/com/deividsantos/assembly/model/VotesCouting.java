package com.deividsantos.assembly.model;

public class VotesCouting {
    private Integer totalVotes;
    private Integer positiveVotes;
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

    public static Builder aVotesCouting() {
        return new Builder();
    }

    public static final class Builder {

        private VotesCouting votesCouting;

        private Builder() {
            votesCouting = new VotesCouting();
        }

        public Builder withTotalVotes(Integer totalVotes) {
            votesCouting.setTotalVotes(totalVotes);
            return this;
        }

        public Builder withPositiveVotes(Integer positiveVotes) {
            votesCouting.setPositiveVotes(positiveVotes);
            return this;
        }

        public Builder withNegativeVotes(Integer negativeVotes) {
            votesCouting.setNegativeVotes(negativeVotes);
            return this;
        }

        public VotesCouting build() {
            return votesCouting;
        }
    }
}
