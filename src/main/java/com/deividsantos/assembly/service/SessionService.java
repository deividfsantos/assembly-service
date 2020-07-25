package com.deividsantos.assembly.service;

import com.deividsantos.assembly.exception.SessionClosedException;
import com.deividsantos.assembly.repository.SessionRepository;
import com.deividsantos.assembly.repository.entity.SessionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Service
public class SessionService {
    private static final Logger logger = LoggerFactory.getLogger(VoteService.class);

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
                .orElseThrow(getSessionClosedException(agendaId));
    }

    private Supplier<SessionClosedException> getSessionClosedException(Integer agendaId) {
        logger.error("Agenda with id {} is closed.", agendaId);
        return SessionClosedException::new;
    }
}
