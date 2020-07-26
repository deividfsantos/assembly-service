package com.deividsantos.assembly.stub;

import com.deividsantos.assembly.model.VotesCouting;
import com.deividsantos.assembly.repository.entity.VoteEntity;
import com.deividsantos.assembly.type.VoteOption;

public class VoteStub {
    public static VotesCouting build() {
        return VotesCouting.aVotesCouting()
                .withNegativeVotes(4)
                .withPositiveVotes(7)
                .withTotalVotes(11)
                .build();
    }

    public static VoteEntity buildEntity() {
        return VoteEntity.aVote()
                .withAssociatedCpf("02565485201")
                .withAssociatedId(12345)
                .withAgendaId(123)
                .withId(12)
                .withVote(VoteOption.Sim)
                .build();
    }
}
