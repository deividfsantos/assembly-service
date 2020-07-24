package com.deividsantos.assembly.api.rest.v1;

import com.deividsantos.assembly.api.rest.v1.input.AgendaInput;
import com.deividsantos.assembly.api.rest.v1.input.VotingInput;
import com.deividsantos.assembly.api.rest.v1.output.AgendaOutput;
import com.deividsantos.assembly.api.rest.v1.output.ResultsOutput;
import com.deividsantos.assembly.api.rest.v1.output.VoteOutput;
import com.deividsantos.assembly.model.Agenda;
import com.deividsantos.assembly.model.Associated;
import com.deividsantos.assembly.model.VotesCouting;
import com.deividsantos.assembly.service.AgendaService;
import com.deividsantos.assembly.service.AssociatedService;
import com.deividsantos.assembly.service.SessionService;
import com.deividsantos.assembly.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/agenda")
public class AgendaApi {

    private final AgendaService agendaService;
    private final SessionService sessionService;
    private final VoteService voteService;
    private final AssociatedService associatedService;
    private final ObjectMapper objectMapper;

    public AgendaApi(AgendaService agendaService, SessionService sessionService, VoteService voteService, AssociatedService associatedService, ObjectMapper objectMapper) {
        this.agendaService = agendaService;
        this.sessionService = sessionService;
        this.voteService = voteService;
        this.associatedService = associatedService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    @ApiOperation(value = "Creates a new agenda for voting.")
    public ResponseEntity<AgendaOutput> createAgenda(@RequestBody AgendaInput agendaInput) {
        final Agenda agenda = objectMapper.convertValue(agendaInput, Agenda.class);
        Integer id = agendaService.create(agenda);
        return ResponseEntity.ok(new AgendaOutput(id));
    }

    @PatchMapping("/{id}/session")
    @ApiOperation(value = "Open a new session for the specified agenda for voting.")
    public ResponseEntity<Void> openSession(@PathVariable Integer id,
                                            @ApiParam(value = "Duration of agenda's session in minutes", defaultValue = "1")
                                            @RequestParam(required = false, defaultValue = "1") Integer durationTime) {
        sessionService.open(id, durationTime);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/vote")
    @ApiOperation(value = "Vote on the specified agenda.")
    public ResponseEntity<VoteOutput> voteOnAgenda(@PathVariable Integer id,
                                                   @RequestBody VotingInput votingInput) {
        final Associated associated = objectMapper.convertValue(votingInput.getAssociated(), Associated.class);
        associatedService.validateAssociatedAbleToVote(associated.getCpf());
        final Integer voteId = voteService.add(id, associated, votingInput.getVote());
        return ResponseEntity.ok(new VoteOutput(voteId));
    }

    @GetMapping("/{id}/results")
    @ApiOperation(value = "Get the final results of the agendas.")
    public ResponseEntity<ResultsOutput> voteOnAgenda(@PathVariable Integer id) {
        final VotesCouting votesCouting = voteService.countVotes(id);
        final ResultsOutput resultsOutput = objectMapper.convertValue(votesCouting, ResultsOutput.class);
        return ResponseEntity.ok(resultsOutput);
    }
}
