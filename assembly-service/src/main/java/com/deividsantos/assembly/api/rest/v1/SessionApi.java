package com.deividsantos.assembly.api.rest.v1;

import com.deividsantos.assembly.service.SessionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/session")
public class SessionApi {
    private static final Logger logger = LoggerFactory.getLogger(SessionApi.class);
    private final SessionService sessionService;

    public SessionApi(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/agenda/{agendaId}")
    @ApiOperation(value = "Open a new session for the specified agenda for voting.")
    public ResponseEntity<Void> openSession(@PathVariable Integer agendaId,
                                            @ApiParam(value = "Duration of agenda's session in minutes.", defaultValue = "1")
                                            @RequestParam(required = false, defaultValue = "1") Integer durationTime) {
        logger.info("Opening session with from agenda with id {}.", agendaId);
        sessionService.open(agendaId, durationTime);
        logger.info("Agenda with id {} opened.", agendaId);
        return ResponseEntity.noContent().build();
    }

}
