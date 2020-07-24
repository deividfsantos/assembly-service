package com.deividsantos.assembly.service;

import com.deividsantos.assembly.exception.AlreadyVotedException;
import com.deividsantos.assembly.mapper.VoteMapper;
import com.deividsantos.assembly.model.Associated;
import com.deividsantos.assembly.model.VotesCouting;
import com.deividsantos.assembly.repository.VoteRepository;
import com.deividsantos.assembly.repository.entity.VoteEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final SessionService sessionService;

    public VoteService(VoteRepository voteRepository, SessionService sessionService) {
        this.voteRepository = voteRepository;
        this.sessionService = sessionService;
    }

    public Integer add(Integer agendaId, Associated associated, String vote) {
        sessionService.validateSessionOpened(agendaId);
        final VoteEntity voteEntity = VoteMapper.map(agendaId, associated, vote);
        try {
            return voteRepository.save(voteEntity)
                    .getId();
        } catch (DataIntegrityViolationException ex) {
            throw new AlreadyVotedException();
        }
    }

    public VotesCouting countVotes(Integer agendaId) {
        final Integer totalVotes = voteRepository.countByAgendaId(agendaId);
        final Integer positiveVotes = voteRepository.countByAgendaIdAndVote(agendaId, "Sim");
        return VotesCouting.aVotesCouting()
                .withTotalVotes(totalVotes)
                .withPositiveVotes(positiveVotes)
                .withNegativeVotes(totalVotes - positiveVotes)
                .build();
    }
}
