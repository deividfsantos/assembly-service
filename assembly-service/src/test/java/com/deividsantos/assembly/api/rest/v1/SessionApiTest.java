package com.deividsantos.assembly.api.rest.v1;

import com.deividsantos.assembly.config.GlobalExceptionHandler;
import com.deividsantos.assembly.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SessionApiTest {

    private final SessionApi sessionApi;
    private final SessionService sessionService;
    private MockMvc mockMvc;

    public SessionApiTest() {
        this.sessionService = mock(SessionService.class);
        this.sessionApi = new SessionApi(sessionService);
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sessionApi)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createAgendaShouldReturnSuccess() throws Exception {
        mockMvc.perform(post("/v1/session/agenda/123")
                .param("durationTime", "5"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        verify(sessionService).open(123, 5);
    }

    @Test
    void createAgendaWithoutOptionalDurationShouldReturnSuccess() throws Exception {
        mockMvc.perform(post("/v1/session/agenda/123"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        verify(sessionService).open(123, 1);
    }
}