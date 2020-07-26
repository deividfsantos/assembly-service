package com.deividsantos.assembly.api.rest.v1;

import com.deividsantos.assembly.api.rest.v1.input.VotingInput;
import com.deividsantos.assembly.api.rest.v1.output.VoteOutput;
import com.deividsantos.assembly.model.Associated;
import com.deividsantos.assembly.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/vote")
public class VoteApi {
    private static final Logger logger = LoggerFactory.getLogger(VoteApi.class);
    private final VoteService voteService;
    private final ObjectMapper objectMapper;

    public VoteApi(VoteService voteService, ObjectMapper objectMapper) {
        this.voteService = voteService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/agenda/{agendaId}")
    @ApiOperation(value = "Vote on the specified agenda.")
    public ResponseEntity<VoteOutput> voteOnAgenda(@PathVariable Integer agendaId,
                                                   @RequestBody @Valid VotingInput votingInput) {
        logger.info("Receiving vote on agenda with id {} from associated {}.", agendaId, votingInput.getAssociated().getId());
        final Associated associated = objectMapper.convertValue(votingInput.getAssociated(), Associated.class);
        final Integer voteId = voteService.add(agendaId, associated, votingInput.getVote());
        logger.info("Vote on agenda with id {} from associated {} received.", agendaId, votingInput.getAssociated().getId());
        return ResponseEntity.ok(new VoteOutput(voteId));
    }

}
