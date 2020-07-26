package com.deividsantos.assembly.service;

import com.deividsantos.assembly.mapper.ResultsEventMapper;
import com.deividsantos.assembly.model.Session;
import com.deividsantos.assembly.model.VotesCouting;
import com.deividsantos.assembly.producer.SessionResultsProducer;
import com.deividsantos.assembly.producer.event.AgendaResultEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SessionJobService {
    private static final Logger logger = LoggerFactory.getLogger(SessionJobService.class);
    private final SessionService sessionService;
    private final VoteService voteService;
    private final SessionResultsProducer sessionResultsProducer;

    public SessionJobService(SessionService sessionService, VoteService voteService, SessionResultsProducer sessionResultsProducer) {
        this.sessionService = sessionService;
        this.voteService = voteService;
        this.sessionResultsProducer = sessionResultsProducer;
    }

    @Scheduled(fixedRate = 150000)
    public void sendSessionClosedEvent() {
        logger.info("Sending session results event.");
        sessionService.findAllExpiredSessions()
                .stream()
                .map(this::countVotesAndChangeSessionStatus)
                .forEach(sessionResultsProducer::produceEvent);
    }

    private AgendaResultEvent countVotesAndChangeSessionStatus(Session openSession) {
        final VotesCouting votesCouting = voteService.countVotes(openSession.getAgendaId());
        sessionService.closeSession(openSession.getId());
        return ResultsEventMapper.map(openSession.getAgendaId(), votesCouting);
    }
}
