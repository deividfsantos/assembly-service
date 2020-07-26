package com.deividsantos.assembly.mapper;

import com.deividsantos.assembly.repository.entity.SessionEntity;
import com.deividsantos.assembly.type.SessionStatus;

import java.time.LocalDateTime;

public class SessionEntityMapper {
    public static SessionEntity map(Integer agendaId, Integer durationTimeMinutes) {
        return SessionEntity.aSessionEntity()
                .withAgendaId(agendaId)
                .withDueDate(LocalDateTime.now().plusMinutes(durationTimeMinutes))
                .withSessionStatus(SessionStatus.OPEN)
                .build();
    }
}
