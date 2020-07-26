package com.deividsantos.assembly.stub;

import com.deividsantos.assembly.model.Agenda;
import com.deividsantos.assembly.repository.entity.AgendaEntity;

public class AgendaStub {
    public static AgendaEntity buildEntity() {
        final AgendaEntity agendaEntity = new AgendaEntity();
        agendaEntity.setDescription("Description of agenda?");
        agendaEntity.setId(123);
        return agendaEntity;
    }

    public static Agenda build() {
        final Agenda agendaEntity = new Agenda();
        agendaEntity.setDescription("Description of agenda?");
        return agendaEntity;
    }
}
