package com.deividsantos.assembly.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer agendaId;
    private String vote;
    private String associatedId;
    private String associatedCpf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Integer agendaId) {
        this.agendaId = agendaId;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(String associatedId) {
        this.associatedId = associatedId;
    }

    public String getAssociatedCpf() {
        return associatedCpf;
    }

    public void setAssociatedCpf(String associatedCpf) {
        this.associatedCpf = associatedCpf;
    }

    public static Builder aVote() {
        return new Builder();
    }

    public static final class Builder {

        private final VoteEntity voteEntity;

        private Builder() {
            voteEntity = new VoteEntity();
        }

        public Builder withId(Integer id) {
            voteEntity.setId(id);
            return this;
        }

        public Builder withAgendaId(Integer agendaId) {
            voteEntity.setAgendaId(agendaId);
            return this;
        }

        public Builder withVote(String vote) {
            voteEntity.setVote(vote);
            return this;
        }

        public Builder withAssociatedId(String associatedId) {
            voteEntity.setAssociatedId(associatedId);
            return this;
        }

        public Builder withAssociatedCpf(String associatedCpf) {
            voteEntity.setAssociatedCpf(associatedCpf);
            return this;
        }

        public VoteEntity build() {
            return voteEntity;
        }
    }
}
