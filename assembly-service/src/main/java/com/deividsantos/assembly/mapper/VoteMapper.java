package com.deividsantos.assembly.mapper;

import com.deividsantos.assembly.model.Associated;
import com.deividsantos.assembly.model.VotesCouting;
import com.deividsantos.assembly.repository.entity.VoteEntity;
import com.deividsantos.assembly.type.VoteOption;

public class VoteMapper {
    public static VoteEntity map(Integer agendaId, Associated associated, VoteOption vote) {
        return VoteEntity.aVote()
                .withAgendaId(agendaId)
                .withAssociatedCpf(associated.getCpf())
                .withAssociatedId(associated.getId())
                .withVote(vote)
                .build();
    }

    public static VotesCouting mapResults(Integer totalVotes, Integer positiveVotes, Integer negativeVotes) {
        return VotesCouting.aVotesCouting()
                .withTotalVotes(totalVotes)
                .withPositiveVotes(positiveVotes)
                .withNegativeVotes(negativeVotes)
                .build();
    }
}
