package com.deividsantos.assembly.service;

import com.deividsantos.assembly.exception.SessionClosedException;
import com.deividsantos.assembly.mapper.ResultsEventMapper;
import com.deividsantos.assembly.model.VotesCouting;
import com.deividsantos.assembly.producer.SessionResultsProducer;
import com.deividsantos.assembly.producer.event.AgendaResultEvent;
import com.deividsantos.assembly.repository.SessionRepository;
import com.deividsantos.assembly.repository.entity.SessionEntity;
import com.deividsantos.assembly.type.SessionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.function.Supplier;

@Service
public class SessionService {
    private static final Logger logger = LoggerFactory.getLogger(VoteService.class);

    private final SessionRepository sessionRepository;
    private final SessionResultsProducer sessionResultsProducer;
    private final VoteService voteService;

    public SessionService(SessionRepository sessionRepository,
                          SessionResultsProducer sessionResultsProducer,
                          @Lazy VoteService voteService) {
        this.sessionRepository = sessionRepository;
        this.sessionResultsProducer = sessionResultsProducer;
        this.voteService = voteService;
    }

    public void open(Integer agendaId, Integer durationTimeMinutes) {
        sessionRepository.save(buildSessionEntity(agendaId, durationTimeMinutes));
    }

    private SessionEntity buildSessionEntity(Integer agendaId, Integer durationTimeMinutes) {
        return SessionEntity.aSessionEntity()
                .withAgendaId(agendaId)
                .withDueDate(LocalDateTime.now().plusMinutes(durationTimeMinutes))
                .withSessionStatus(SessionStatus.OPEN)
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

    @Transactional
    @Scheduled(fixedRate = 200000)
    public void sendSessionClosedEvent() {
        logger.info("Sending session results event.");
        sessionRepository.findAllByStatus(SessionStatus.OPEN)
                .stream()
                .filter(openSession -> !LocalDateTime.now().isBefore(openSession.getDueDate()))
                .map(this::countVotesAndChangeSessionStatus)
                .forEach(sessionResultsProducer::produceEvent);
    }

    private AgendaResultEvent countVotesAndChangeSessionStatus(SessionEntity openSession) {
        final VotesCouting votesCouting = voteService.countVotes(openSession.getAgendaId());
        openSession.setStatus(SessionStatus.CLOSED);
        return ResultsEventMapper.map(openSession.getAgendaId(), votesCouting);
    }
}
