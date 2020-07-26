package com.deividsantos.assembly.service;

import com.deividsantos.assembly.producer.SessionResultsProducer;
import com.deividsantos.assembly.producer.event.AgendaResultEvent;
import com.deividsantos.assembly.stub.SessionStub;
import com.deividsantos.assembly.stub.VoteStub;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SessionJobServiceTest {

    private final SessionService sessionService;
    private final VoteService voteService;
    private final SessionResultsProducer sessionResultsProducer;
    private final SessionJobService sessionJobService;

    public SessionJobServiceTest() {
        this.sessionService = mock(SessionService.class);
        this.voteService = mock(VoteService.class);
        this.sessionResultsProducer = mock(SessionResultsProducer.class);
        this.sessionJobService = new SessionJobService(sessionService, voteService, sessionResultsProducer);
    }

    @Test
    void name() {
        ArgumentCaptor<AgendaResultEvent> captor = ArgumentCaptor.forClass(AgendaResultEvent.class);
        when(sessionService.findAllExpiredSessions()).thenReturn(Collections.singletonList(SessionStub.buildPastDueDate()));
        when(voteService.countVotes(123)).thenReturn(VoteStub.build());

        sessionJobService.sendSessionClosedEvent();

        verify(sessionResultsProducer).produceEvent(captor.capture());
        verify(sessionService).closeSession(2);

        final AgendaResultEvent resultsExpected = captor.getValue();
        assertAll(() -> assertEquals(123, resultsExpected.getAgendaId()),
                () -> assertEquals(4, resultsExpected.getResult().getNegativeVotes()),
                () -> assertEquals(7, resultsExpected.getResult().getPositiveVotes()),
                () -> assertEquals(11, resultsExpected.getResult().getTotalVotes()));
    }
}