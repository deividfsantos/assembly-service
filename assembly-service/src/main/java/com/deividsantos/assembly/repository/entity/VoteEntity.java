package com.deividsantos.assembly.repository.entity;

import com.deividsantos.assembly.type.VoteOption;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "vote")
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer agendaId;
    @Enumerated(EnumType.STRING)
    private VoteOption vote;
    private Integer associatedId;
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

    public VoteOption getVote() {
        return vote;
    }

    public void setVote(VoteOption vote) {
        this.vote = vote;
    }

    public Integer getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(Integer associatedId) {
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

        public Builder withVote(VoteOption vote) {
            voteEntity.setVote(vote);
            return this;
        }

        public Builder withAssociatedId(Integer associatedId) {
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
