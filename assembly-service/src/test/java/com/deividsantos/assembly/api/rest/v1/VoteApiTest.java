package com.deividsantos.assembly.api.rest.v1;

import com.deividsantos.assembly.config.GlobalExceptionHandler;
import com.deividsantos.assembly.model.Associated;
import com.deividsantos.assembly.service.VoteService;
import com.deividsantos.assembly.type.VoteOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteApiTest {

    private final VoteApi voteApi;
    private final VoteService voteService;
    private final ObjectMapper objectMapper;
    private MockMvc mockMvc;

    public VoteApiTest() {
        this.voteService = mock(VoteService.class);
        this.objectMapper = spy(ObjectMapper.class);
        this.voteApi = new VoteApi(voteService, objectMapper);
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(voteApi)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void voteWithValidParamsShouldReturnSuccess() throws Exception {
        String voteRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/voteRequest.json")));
        String expectedVoteReceiptResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/expectedVoteReceiptResponse.json")));

        when(voteService.add(eq(123), any(Associated.class), eq(VoteOption.Sim))).thenReturn(321);

        mockMvc.perform(post("/v1/vote/agenda/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(voteRequest))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedVoteReceiptResponse))
                .andReturn();
    }

    @Test
    void voteWithoutAssociatedIdShouldReturnBadRequest() throws Exception {
        String voteWithoutAssociatedIdRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/voteWithoutAssociatedIdRequest.json")));
        String expectedResponseErrorWithoutMandatoryParams = new String(Files.readAllBytes(Paths.get("src/test/resources/expectedResponseErrorWithoutAssociatedId.json")));

        when(voteService.add(eq(123), any(Associated.class), eq(VoteOption.Sim))).thenReturn(321);

        mockMvc.perform(post("/v1/vote/agenda/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(voteWithoutAssociatedIdRequest))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(expectedResponseErrorWithoutMandatoryParams))
                .andReturn();
    }

    @Test
    void voteWithoutVoteOptionShouldReturnBadRequest() throws Exception {
        String voteWithoutVoteOptionRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/voteWithoutVoteOptionRequest.json")));
        String expectedResponseErrorWithoutMandatoryParams = new String(Files.readAllBytes(Paths.get("src/test/resources/expectedResponseErrorWithoutVoteOption.json")));

        when(voteService.add(eq(123), any(Associated.class), eq(VoteOption.Sim))).thenReturn(321);

        mockMvc.perform(post("/v1/vote/agenda/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(voteWithoutVoteOptionRequest))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(expectedResponseErrorWithoutMandatoryParams))
                .andReturn();
    }
}