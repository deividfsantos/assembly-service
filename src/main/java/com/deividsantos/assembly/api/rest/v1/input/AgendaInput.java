package com.deividsantos.assembly.api.rest.v1.input;

import io.swagger.annotations.ApiModelProperty;

public class AgendaInput {

    @ApiModelProperty(value = "Description of the agenda.")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
