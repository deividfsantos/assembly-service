package com.deividsantos.assembly.api.rest.v1.input;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class AgendaInput {

    @ApiModelProperty(value = "Description of the agenda.")
    @NotBlank(message = "Agenda description is mandatory.")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
