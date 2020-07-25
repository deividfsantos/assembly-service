package com.deividsantos.assembly.mapper;

import com.deividsantos.assembly.model.VotesCouting;
import com.deividsantos.assembly.producer.event.AgendaResultEvent;
import com.deividsantos.assembly.producer.event.ResultEvent;

public class ResultsEventMapper {
    public static AgendaResultEvent map(Integer agendaId, VotesCouting votesCouting) {
        return AgendaResultEvent.anAgendaResultEvent()
                .withAgendaId(agendaId)
                .withResult(buildResults(votesCouting))
                .build();
    }

    private static ResultEvent buildResults(VotesCouting votesCouting) {
        return ResultEvent.aResultEvent()
                .withNegativeVotes(votesCouting.getNegativeVotes())
                .withPositiveVotes(votesCouting.getPositiveVotes())
                .withTotalVotes(votesCouting.getTotalVotes())
                .build();
    }
}
