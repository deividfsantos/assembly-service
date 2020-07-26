package com.deividsantos.assembly.api.rest.v1;

import com.deividsantos.assembly.config.GlobalExceptionHandler;
import com.deividsantos.assembly.exception.ErrorTranslator;
import com.deividsantos.assembly.service.AgendaService;
import com.deividsantos.assembly.service.VoteService;
import com.deividsantos.assembly.stub.VoteStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AgendaApiTest {

    private final AgendaApi agendaApi;
    private final VoteService voteService;
    private final AgendaService agendaService;
    private final ObjectMapper objectMapper;
    private MockMvc mockMvc;

    public AgendaApiTest() {
        this.objectMapper = spy(ObjectMapper.class);
        this.voteService = mock(VoteService.class);
        this.agendaService = mock(AgendaService.class);
        this.agendaApi = new AgendaApi(agendaService, voteService, objectMapper);
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(agendaApi)
                .setControllerAdvice(new GlobalExceptionHandler(new ErrorTranslator()))
                .build();
    }

    @Test
    void createAgendaShouldReturnSuccess() throws Exception {
        String agendaCreationBody = new String(Files.readAllBytes(Paths.get("src/test/resources/agendaCreationRequest.json")));
        String expectedAgendaCreationResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/expectedAgendaCreationResponse.json")));

        when(agendaService.create(any())).thenReturn(1);

        mockMvc.perform(post("/v1/agenda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agendaCreationBody))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedAgendaCreationResponse))
                .andReturn();
    }

    @Test
    void createAgendaWithoutDescriptionShouldReturnBadRequest() throws Exception {
        String agendaCreationBody = new String(Files.readAllBytes(Paths.get("src/test/resources/agendaCreationBodyWithoutDescriptionRequest.json")));
        String expectedResponseErrorWithoutMandatoryParams = new String(Files.readAllBytes(Paths.get("src/test/resources/expectedResponseErrorWithoutAgendaDescription.json")));

        mockMvc.perform(post("/v1/agenda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agendaCreationBody))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(expectedResponseErrorWithoutMandatoryParams))
                .andReturn();
    }

    @Test
    void getAgendaResults() throws Exception {
        String expectedAgendaResultsResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/expectedAgendaResultsResponse.json")));

        when(voteService.countVotes(123)).thenReturn(VoteStub.build());

        mockMvc.perform(get("/v1/agenda/123/results"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedAgendaResultsResponse))
                .andReturn();
    }
}