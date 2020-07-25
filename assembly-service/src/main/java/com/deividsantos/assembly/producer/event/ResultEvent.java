package com.deividsantos.assembly.producer.event;

public class ResultEvent {
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

    public static Builder aResultEvent() {
        return new Builder();
    }

    public static final class Builder {

        private ResultEvent resultEvent;

        private Builder() {
            resultEvent = new ResultEvent();
        }

        public Builder withTotalVotes(Integer totalVotes) {
            resultEvent.setTotalVotes(totalVotes);
            return this;
        }

        public Builder withPositiveVotes(Integer positiveVotes) {
            resultEvent.setPositiveVotes(positiveVotes);
            return this;
        }

        public Builder withNegativeVotes(Integer negativeVotes) {
            resultEvent.setNegativeVotes(negativeVotes);
            return this;
        }

        public ResultEvent build() {
            return resultEvent;
        }
    }
}
