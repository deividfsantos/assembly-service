package com.deividsantos.assembly.service;

import com.deividsantos.assembly.exception.AgendaNotFoundException;
import com.deividsantos.assembly.model.Agenda;
import com.deividsantos.assembly.repository.AgendaRepository;
import com.deividsantos.assembly.repository.entity.AgendaEntity;
import com.deividsantos.assembly.stub.AgendaStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class AgendaServiceTest {

    private final AgendaService agendaService;
    private final AgendaRepository agendaRepository;
    private final ObjectMapper objectMapper;

    public AgendaServiceTest() {
        this.agendaRepository = mock(AgendaRepository.class);
        this.objectMapper = spy(Jackson2ObjectMapperBuilder.json().build());
        this.agendaService = new AgendaService(agendaRepository, objectMapper);
    }

    @Test
    void findAgendaByIdSavedShouldReturnAgendaWithDescription() {
        when(agendaRepository.findById(123)).thenReturn(Optional.of(AgendaStub.buildEntity()));

        final Agenda agenda = agendaService.get(123);

        assertEquals("Description of agenda?", agenda.getDescription());
    }

    @Test
    void findUnknownIdShouldThrowsAgendaNotFoundException() {
        when(agendaRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(AgendaNotFoundException.class, () -> agendaService.get(123));
    }

    @Test
    void createAgendaWithValidAgendaShouldSaveNewAgenda() {
        ArgumentCaptor<AgendaEntity> captor = ArgumentCaptor.forClass(AgendaEntity.class);
        when(agendaRepository.save(captor.capture())).thenReturn(AgendaStub.buildEntity());

        final Integer agendaId = agendaService.create(AgendaStub.build());

        final AgendaEntity capturedAgenda = captor.getValue();
        assertAll(() -> assertEquals(123, agendaId),
                () -> assertEquals("Description of agenda?", capturedAgenda.getDescription()),
                () -> assertNull(capturedAgenda.getId()));
    }
}