package com.deividsantos.assembly.service;

import com.deividsantos.assembly.exception.SessionClosedException;
import com.deividsantos.assembly.repository.SessionRepository;
import com.deividsantos.assembly.repository.entity.SessionEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void open(Integer agendaId, Integer durationTimeMinutes) {
        sessionRepository.save(buildSession(agendaId, durationTimeMinutes));
    }

    private SessionEntity buildSession(Integer agendaId, Integer durationTimeMinutes) {
        return SessionEntity.aSessionEntity()
                .withAgendaId(agendaId)
                .withDueDate(LocalDateTime.now().plusMinutes(durationTimeMinutes))
                .build();
    }

    public void validateSessionOpened(Integer agendaId) {
        sessionRepository.findAllByAgendaId(agendaId)
                .stream()
                .map(SessionEntity::getDueDate)
                .filter(dueDate -> LocalDateTime.now().isBefore(dueDate))
                .findFirst()
                .orElseThrow(SessionClosedException::new);
    }
}
