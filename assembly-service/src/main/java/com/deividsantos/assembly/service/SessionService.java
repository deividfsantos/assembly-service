package com.deividsantos.assembly.service;

import com.deividsantos.assembly.exception.AgendaNotFoundException;
import com.deividsantos.assembly.exception.SessionAlreadyOpenException;
import com.deividsantos.assembly.exception.SessionNotOpenException;
import com.deividsantos.assembly.mapper.SessionEntityMapper;
import com.deividsantos.assembly.model.Session;
import com.deividsantos.assembly.repository.SessionRepository;
import com.deividsantos.assembly.repository.entity.SessionEntity;
import com.deividsantos.assembly.type.SessionStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);
    private final SessionRepository sessionRepository;
    private final ObjectMapper objectMapper;

    public SessionService(SessionRepository sessionRepository,
                          ObjectMapper objectMapper) {
        this.sessionRepository = sessionRepository;
        this.objectMapper = objectMapper;
    }

    public void open(Integer agendaId, Integer durationTimeMinutes) {
        validateSessionNotOpened(agendaId);

        final SessionEntity sessionEntity = SessionEntityMapper.map(agendaId, durationTimeMinutes);
        try {
            sessionRepository.save(sessionEntity);
        } catch (DataIntegrityViolationException e) {
            logger.error("Agenda with id {} not found for open session.", agendaId);
            throw new AgendaNotFoundException();
        }
    }

    private void validateSessionNotOpened(Integer agendaId) {
        getOpenedSession(agendaId).ifPresent(session -> {
            logger.error("There is already an open session for the agenda {}.", agendaId);
            throw new SessionAlreadyOpenException();
        });
    }

    public void validateSessionOpened(Integer agendaId) {
        getOpenedSession(agendaId).orElseThrow(() -> {
            logger.error("Session from agenda with id {} is not open.", agendaId);
            return new SessionNotOpenException();
        });
    }

    private Optional<LocalDateTime> getOpenedSession(Integer agendaId) {
        return sessionRepository.findAllByAgendaId(agendaId)
                .stream()
                .map(SessionEntity::getDueDate)
                .filter(this::isClosedSession)
                .findFirst();
    }

    public List<Session> findAllExpiredSessions() {
        return sessionRepository.findAllByStatus(SessionStatus.OPEN)
                .stream()
                .map(sessionEntity -> objectMapper.convertValue(sessionEntity, Session.class))
                .filter(openSession -> !isClosedSession(openSession.getDueDate()))
                .collect(Collectors.toList());
    }

    private boolean isClosedSession(LocalDateTime dueDate) {
        return LocalDateTime.now().isBefore(dueDate);
    }

    @Transactional
    public void closeSession(Integer id) {
        sessionRepository.findById(id).ifPresent(sessionEntity -> sessionEntity.setStatus(SessionStatus.CLOSED));
    }
}
