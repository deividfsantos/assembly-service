package com.deividsantos.assembly.service;

import com.deividsantos.assembly.exception.AgendaNotFoundException;
import com.deividsantos.assembly.exception.AlreadyVotedException;
import com.deividsantos.assembly.exception.IncorrectCpfFormatException;
import com.deividsantos.assembly.model.VotesCouting;
import com.deividsantos.assembly.repository.VoteRepository;
import com.deividsantos.assembly.repository.entity.VoteEntity;
import com.deividsantos.assembly.stub.AssociatedStub;
import com.deividsantos.assembly.stub.VoteStub;
import com.deividsantos.assembly.type.VoteOption;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VoteServiceTest {

    private final VoteService voteService;
    private final SessionService sessionService;
    private final AgendaService agendaService;
    private final AssociatedService associatedService;
    private final VoteRepository voteRepository;

    VoteServiceTest() {
        this.sessionService = mock(SessionService.class);
        this.agendaService = mock(AgendaService.class);
        this.voteRepository = mock(VoteRepository.class);
        this.associatedService = mock(AssociatedService.class);
        this.voteService = new VoteService(voteRepository, sessionService, associatedService, agendaService);
    }

    @Test
    void createVoteWithValidAssociatedAndSessionAndAgendaShouldReturnVoteReceipt() {
        ArgumentCaptor<VoteEntity> captor = ArgumentCaptor.forClass(VoteEntity.class);
        when(voteRepository.save(captor.capture())).thenReturn(VoteStub.buildEntity());

        final Integer receipt = voteService.add(123, AssociatedStub.build(), VoteOption.Sim);

        final VoteEntity savedVote = captor.getValue();
        assertAll(() -> assertEquals(12, receipt),
                () -> assertEquals(123, savedVote.getAgendaId()),
                () -> assertEquals("02565485201", savedVote.getAssociatedCpf()),
                () -> assertEquals(12345, savedVote.getAssociatedId()),
                () -> assertEquals(VoteOption.Sim, savedVote.getVote()),
                () -> assertNull(savedVote.getId()));
    }

    @Test
    void createVoteWithInvalidAgendaShouldThrowsAgendaNotFoundException() {
        when(agendaService.get(123)).thenThrow(AgendaNotFoundException.class);

        assertThrows(AgendaNotFoundException.class, () -> voteService.add(123, AssociatedStub.build(), VoteOption.Sim));
    }

    @Test
    void createVoteWithValidAgendaAndInvalidAssociatedShouldThrowsIncorrectCpfFormatException() {
        doThrow(IncorrectCpfFormatException.class).when(associatedService).validateAssociatedAbleToVote("02565485201");

        assertThrows(IncorrectCpfFormatException.class, () -> voteService.add(123, AssociatedStub.build(), VoteOption.Sim));
    }

    @Test
    void createVoteWithValidAgendaAndAssociatedThatAlreadyVotedShouldThrowsAlreadyVotedException() {
        when(voteRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(AlreadyVotedException.class, () -> voteService.add(123, AssociatedStub.build(), VoteOption.Sim));
    }

    @Test
    void countVotesWithValidAgendaShouldReturnResults() {
        when(voteRepository.countByAgendaId(123)).thenReturn(10);
        when(voteRepository.countByAgendaIdAndVote(123, VoteOption.Sim)).thenReturn(3);

        final VotesCouting votesCouting = voteService.countVotes(123);

        assertEquals(10, votesCouting.getTotalVotes());
        assertEquals(3, votesCouting.getPositiveVotes());
        assertEquals(7, votesCouting.getNegativeVotes());
    }

    @Test
    void countVotesWithInvalidAgendaShouldReturnAgendaNotFoundException() {
        when(agendaService.get(123)).thenThrow(AgendaNotFoundException.class);

        assertThrows(AgendaNotFoundException.class, () -> voteService.countVotes(123));
    }
}