package com.deividsantos.assembly.service;

import com.deividsantos.assembly.model.Agenda;
import com.deividsantos.assembly.model.Associated;
import com.deividsantos.assembly.repository.AgendaRepository;
import com.deividsantos.assembly.repository.entity.AgendaEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final ObjectMapper objectMapper;

    public AgendaService(AgendaRepository agendaRepository, ObjectMapper objectMapper) {
        this.agendaRepository = agendaRepository;
        this.objectMapper = objectMapper;
    }

    public Integer create(Agenda agenda) {
        final AgendaEntity agendaEntity = objectMapper.convertValue(agenda, AgendaEntity.class);
        return agendaRepository.save(agendaEntity)
                .getId();
    }

    public void openSession(Integer id, String durationTime) {
        //open session
    }
}