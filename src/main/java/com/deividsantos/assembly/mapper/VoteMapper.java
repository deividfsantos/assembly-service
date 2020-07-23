package com.deividsantos.assembly.mapper;

import com.deividsantos.assembly.model.Associated;
import com.deividsantos.assembly.repository.entity.VoteEntity;

public class VoteMapper {
    public static VoteEntity map(Integer agendaId, Associated associated, String vote) {
        return VoteEntity.aVote()
                .withAgendaId(agendaId)
                .withAssociatedCpf(associated.getCpf())
                .withAssociatedId(associated.getId())
                .withVote(vote)
                .build();
    }
}
