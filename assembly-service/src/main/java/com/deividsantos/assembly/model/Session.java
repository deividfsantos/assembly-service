package com.deividsantos.assembly.model;

import com.deividsantos.assembly.type.SessionStatus;

import java.time.LocalDateTime;

public class Session {
    private Integer id;
    private Integer agendaId;
    private LocalDateTime dueDate;
    private SessionStatus status;

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

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public static Builder aSession() {
        return new Builder();
    }

    public static final class Builder {

        private Session session;

        private Builder() {
            session = new Session();
        }

        public Builder withId(Integer id) {
            session.setId(id);
            return this;
        }

        public Builder withAgendaId(Integer agendaId) {
            session.setAgendaId(agendaId);
            return this;
        }

        public Builder withDueDate(LocalDateTime dueDate) {
            session.setDueDate(dueDate);
            return this;
        }

        public Builder withStatus(SessionStatus status) {
            session.setStatus(status);
            return this;
        }

        public Session build() {
            return session;
        }
    }
}
