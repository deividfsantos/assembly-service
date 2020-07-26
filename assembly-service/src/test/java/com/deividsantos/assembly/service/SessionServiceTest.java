package com.deividsantos.assembly.service;

import com.deividsantos.assembly.exception.AgendaNotFoundException;
import com.deividsantos.assembly.exception.SessionAlreadyOpenException;
import com.deividsantos.assembly.exception.SessionNotOpenException;
import com.deividsantos.assembly.model.Session;
import com.deividsantos.assembly.repository.SessionRepository;
import com.deividsantos.assembly.repository.entity.SessionEntity;
import com.deividsantos.assembly.stub.SessionStub;
import com.deividsantos.assembly.type.SessionStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SessionServiceTest {

    private final SessionService sessionService;
    private final SessionRepository sessionRepository;
    private final ObjectMapper objectMapper;

    public SessionServiceTest() {
        this.sessionRepository = mock(SessionRepository.class);
        this.objectMapper = spy(Jackson2ObjectMapperBuilder.json().build());
        this.sessionService = new SessionService(sessionRepository, objectMapper);
    }

    @Test
    void openSessionShouldOpenSessionWithFutureDueDate() {
        ArgumentCaptor<SessionEntity> captor = ArgumentCaptor.forClass(SessionEntity.class);

        sessionService.open(123, 10);

        verify(sessionRepository).save(captor.capture());

        final SessionEntity sessionEntity = captor.getValue();
        assertAll(() -> assertEquals(123, sessionEntity.getAgendaId()),
                () -> assertEquals(SessionStatus.OPEN, sessionEntity.getStatus()),
                () -> assertTrue(sessionEntity.getDueDate().isAfter(LocalDateTime.now())),
                () -> assertNull(sessionEntity.getId()));
    }

    @Test
    void openSessionWithInvalidAgendaSouldThrowsAgendaNotFoundException() {
        when(sessionRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(AgendaNotFoundException.class, () -> sessionService.open(123, 10));
    }

    @Test
    void openSessionShouldWithAlreadyOpenedSesseionShouldThrowsSessionAlreadyOpenException() {
        when(sessionRepository.findAllByAgendaId(123)).thenReturn(Arrays.asList(SessionStub.build(), SessionStub.build()));

        assertThrows(SessionAlreadyOpenException.class, () -> sessionService.open(123, 10));
    }

    @Test
    void validateSessionOpenedWithFutureDueDateShouldReturnSuccess() {
        when(sessionRepository.findAllByAgendaId(123)).thenReturn(Arrays.asList(SessionStub.build(), SessionStub.build()));

        sessionService.validateSessionOpened(123);
    }

    @Test
    void validateSessionOpenedWithPastDueDateShouldThrowsSessionNotOpenException() {
        when(sessionRepository.findAllByAgendaId(123)).thenReturn(Collections.singletonList(SessionStub.buildPastDueDate()));

        assertThrows(SessionNotOpenException.class, () -> sessionService.validateSessionOpened(123));
    }

    @Test
    void findAllExpiredSessionsShouldReturnExpiredSessions() {
        when(sessionRepository.findAllByStatus(SessionStatus.OPEN))
                .thenReturn(Arrays.asList(SessionStub.buildPastDueDate(), SessionStub.build()));

        final List<Session> allExpiredSessions = sessionService.findAllExpiredSessions();

        assertAll(() -> assertEquals(1, allExpiredSessions.size()),
                () -> assertEquals(123, allExpiredSessions.get(0).getAgendaId()),
                () -> assertNotNull(allExpiredSessions.get(0).getDueDate()),
                () -> assertEquals(2, allExpiredSessions.get(0).getId()));
    }

    @Test
    void closeSessionShouldChangeEntityToClosed() {
        final SessionEntity sessionEntity = SessionStub.build();
        when(sessionRepository.findById(123)).thenReturn(Optional.of(sessionEntity));
        sessionService.closeSession(123);

        assertEquals(SessionStatus.CLOSED, sessionEntity.getStatus());
    }
}