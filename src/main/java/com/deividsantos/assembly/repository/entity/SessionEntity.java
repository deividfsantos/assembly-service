package com.deividsantos.assembly.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "session")
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer agendaId;
    private LocalDateTime dueDate;

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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public static Builder aSessionEntity() {
        return new Builder();
    }

    public static final class Builder {

        private SessionEntity sessionEntity;

        private Builder() {
            sessionEntity = new SessionEntity();
        }

        public Builder withId(Integer id) {
            sessionEntity.setId(id);
            return this;
        }

        public Builder withAgendaId(Integer agendaId) {
            sessionEntity.setAgendaId(agendaId);
            return this;
        }

        public Builder withDueDate(LocalDateTime dueDate) {
            sessionEntity.setDueDate(dueDate);
            return this;
        }

        public SessionEntity build() {
            return sessionEntity;
        }
    }
}
