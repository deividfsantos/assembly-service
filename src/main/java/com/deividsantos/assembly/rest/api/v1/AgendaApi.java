package com.deividsantos.assembly.rest.api.v1;

import com.deividsantos.assembly.model.Agenda;
import com.deividsantos.assembly.model.Associated;
import com.deividsantos.assembly.rest.api.v1.input.AgendaInput;
import com.deividsantos.assembly.rest.api.v1.input.VotingInput;
import com.deividsantos.assembly.rest.api.v1.output.AgendaOutput;
import com.deividsantos.assembly.rest.api.v1.output.VoteOutput;
import com.deividsantos.assembly.service.AgendaService;
import com.deividsantos.assembly.service.AssociatedService;
import com.deividsantos.assembly.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("v1/agenda")
public class AgendaApi {

    private final AgendaService agendaService;
    private final VoteService voteService;
    private final AssociatedService associatedService;
    private final ObjectMapper objectMapper;

    public AgendaApi(AgendaService agendaService, VoteService voteService, AssociatedService associatedService, ObjectMapper objectMapper) {
        this.agendaService = agendaService;
        this.voteService = voteService;
        this.associatedService = associatedService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<AgendaOutput> createAgenda(@RequestBody AgendaInput agendaInput) {
        final Agenda agenda = objectMapper.convertValue(agendaInput, Agenda.class);
        Integer id = agendaService.create(agenda);
        return ResponseEntity.ok(new AgendaOutput(id));
    }

    @PatchMapping("/{id}/session")
    public ResponseEntity<Void> openSession(@PathVariable Integer id,
                                            @RequestParam(required = false) String durationTime) {
        agendaService.openSession(id, durationTime);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/vote")
    public ResponseEntity<VoteOutput> voteOnAgenda(@PathVariable Integer id,
                                                   @RequestBody VotingInput votingInput) {
        final Associated associated = objectMapper.convertValue(votingInput.getAssociated(), Associated.class);
        associatedService.validateAssociatedAbleToVote(associated.getCpf());
        final Integer voteId = voteService.add(id, associated, votingInput.getVote());
        return ResponseEntity.ok(new VoteOutput(voteId));
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<VoteOutput> voteOnAgenda(@PathVariable Integer id) {
        voteService.countVotes(id);
        return ResponseEntity.ok().build();
    }
}
