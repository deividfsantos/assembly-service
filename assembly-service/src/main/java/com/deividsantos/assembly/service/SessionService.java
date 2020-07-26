package com.deividsantos.assembly.service;

import com.deividsantos.assembly.exception.AgendaNotFoundException;
import com.deividsantos.assembly.exception.SessionClosedException;
import com.deividsantos.assembly.mapper.ResultsEventMapper;
import com.deividsantos.assembly.mapper.SessionEntityMapper;
import com.deividsantos.assembly.model.VotesCouting;
import com.deividsantos.assembly.producer.SessionResultsProducer;
import com.deividsantos.assembly.producer.event.AgendaResultEvent;
import com.deividsantos.assembly.repository.SessionRepository;
import com.deividsantos.assembly.repository.entity.SessionEntity;
import com.deividsantos.assembly.type.SessionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.function.Supplier;

@Service
public class SessionService {
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);
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
        final SessionEntity sessionEntity = SessionEntityMapper.map(agendaId, durationTimeMinutes);
        try {
            sessionRepository.save(sessionEntity);
        } catch (DataIntegrityViolationException e) {
            logger.error("Agenda with id {} not found for open session.", agendaId);
            throw new AgendaNotFoundException();
        }
    }

    public void validateSessionOpened(Integer agendaId) {
        sessionRepository.findAllByAgendaId(agendaId)
                .stream()
                .map(SessionEntity::getDueDate)
                .filter(this::isClosedForVote)
                .findFirst()
                .orElseThrow(getSessionClosedException(agendaId));
    }

    private Supplier<SessionClosedException> getSessionClosedException(Integer agendaId) {
        logger.error("Agenda with id {} is not open.", agendaId);
        return SessionClosedException::new;
    }

    @Transactional
    @Scheduled(fixedRate = 200000)
    public void sendSessionClosedEvent() {
        logger.info("Sending session results event.");
        sessionRepository.findAllByStatus(SessionStatus.OPEN)
                .stream()
                .filter(openSession -> !isClosedForVote(openSession.getDueDate()))
                .map(this::countVotesAndChangeSessionStatus)
                .forEach(sessionResultsProducer::produceEvent);
    }

    private AgendaResultEvent countVotesAndChangeSessionStatus(SessionEntity openSession) {
        final VotesCouting votesCouting = voteService.countVotes(openSession.getAgendaId());
        openSession.setStatus(SessionStatus.CLOSED);
        return ResultsEventMapper.map(openSession.getAgendaId(), votesCouting);
    }

    private boolean isClosedForVote(LocalDateTime dueDate) {
        return LocalDateTime.now().isBefore(dueDate);
    }
}
