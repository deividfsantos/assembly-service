package com.deividsantos.assembly.stub;

import com.deividsantos.assembly.repository.entity.SessionEntity;
import com.deividsantos.assembly.type.SessionStatus;

import java.time.LocalDateTime;

public class SessionStub {
    public static SessionEntity build() {
        return SessionEntity.aSessionEntity()
                .withSessionStatus(SessionStatus.OPEN)
                .withAgendaId(123)
                .withId(1)
                .withDueDate(LocalDateTime.now().plusMinutes(5))
                .build();
    }

    public static SessionEntity buildPastDueDate() {
        return SessionEntity.aSessionEntity()
                .withSessionStatus(SessionStatus.OPEN)
                .withAgendaId(123)
                .withId(2)
                .withDueDate(LocalDateTime.now().minusMinutes(5))
                .build();
    }
}
