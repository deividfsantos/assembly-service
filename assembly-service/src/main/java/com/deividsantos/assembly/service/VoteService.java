package com.deividsantos.assembly.service;

import com.deividsantos.assembly.exception.AlreadyVotedException;
import com.deividsantos.assembly.mapper.VoteMapper;
import com.deividsantos.assembly.model.Associated;
import com.deividsantos.assembly.model.VotesCouting;
import com.deividsantos.assembly.repository.VoteRepository;
import com.deividsantos.assembly.repository.entity.VoteEntity;
import com.deividsantos.assembly.type.VoteOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    private static final Logger logger = LoggerFactory.getLogger(VoteService.class);
    private final VoteRepository voteRepository;
    private final SessionService sessionService;
    private final AssociatedService associatedService;
    private final AgendaService agendaService;

    public VoteService(VoteRepository voteRepository,
                       SessionService sessionService,
                       AssociatedService associatedService,
                       AgendaService agendaService) {
        this.voteRepository = voteRepository;
        this.sessionService = sessionService;
        this.associatedService = associatedService;
        this.agendaService = agendaService;
    }

    public Integer add(Integer agendaId, Associated associated, VoteOption vote) {
        agendaService.get(agendaId);
        associatedService.validateAssociatedAbleToVote(associated.getCpf());
        sessionService.validateSessionOpened(agendaId);
        final VoteEntity voteEntity = VoteMapper.map(agendaId, associated, vote);
        try {
            return voteRepository.save(voteEntity).getId();
        } catch (DataIntegrityViolationException ex) {
            logger.error("The associated {} already voted on agenda {}.", associated.getId(), agendaId, ex);
            throw new AlreadyVotedException();
        }
    }

    public VotesCouting countVotes(Integer agendaId) {
        agendaService.get(agendaId);
        final Integer totalVotes = voteRepository.countByAgendaId(agendaId);
        final Integer positiveVotes = voteRepository.countByAgendaIdAndVote(agendaId, VoteOption.Sim);
        final Integer negativeVotes = totalVotes - positiveVotes;
        return VoteMapper.mapResults(totalVotes, positiveVotes, negativeVotes);
    }

}
