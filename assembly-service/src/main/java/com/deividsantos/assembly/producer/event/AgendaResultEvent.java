package com.deividsantos.assembly.producer.event;

public class AgendaResultEvent {
    private Integer agendaId;
    private ResultEvent result;

    public Integer getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Integer agendaId) {
        this.agendaId = agendaId;
    }

    public ResultEvent getResult() {
        return result;
    }

    public void setResult(ResultEvent result) {
        this.result = result;
    }

    public static Builder anAgendaResultEvent() {
        return new Builder();
    }

    public static final class Builder {

        private AgendaResultEvent agendaResultEvent;

        private Builder() {
            agendaResultEvent = new AgendaResultEvent();
        }

        public Builder withAgendaId(Integer agendaId) {
            agendaResultEvent.setAgendaId(agendaId);
            return this;
        }

        public Builder withResult(ResultEvent result) {
            agendaResultEvent.setResult(result);
            return this;
        }

        public AgendaResultEvent build() {
            return agendaResultEvent;
        }
    }
}
