package com.deividsantos.assembly.service;

import com.deividsantos.assembly.mapper.VoteMapper;
import com.deividsantos.assembly.model.Associated;
import com.deividsantos.assembly.repository.VoteRepository;
import com.deividsantos.assembly.repository.entity.VoteEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final ObjectMapper objectMapper;

    public VoteService(VoteRepository voteRepository, ObjectMapper objectMapper) {
        this.voteRepository = voteRepository;
        this.objectMapper = objectMapper;
    }

    public Integer add(Integer agendaId, Associated associated, String vote) {
        final VoteEntity voteEntity = VoteMapper.map(agendaId, associated, vote);
        return voteRepository.save(voteEntity).getId();
    }

    public Integer countVotes(Integer agendaId) {
        return null;
    }
}
