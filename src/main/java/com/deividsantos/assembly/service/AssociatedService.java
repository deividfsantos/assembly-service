package com.deividsantos.assembly.service;

import com.deividsantos.assembly.client.UserInfoClient;
import com.deividsantos.assembly.repository.entity.AgendaEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class AssociatedService {

    private final UserInfoClient userInfoClient;
    private final ObjectMapper objectMapper;

    public AssociatedService(UserInfoClient userInfoClient, ObjectMapper objectMapper) {
        this.userInfoClient = userInfoClient;
        this.objectMapper = objectMapper;
    }

    public void validateAssociatedAbleToVote(String cpf) {
        userInfoClient.validate(cpf);
    }
}
