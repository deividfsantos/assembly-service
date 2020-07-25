package com.deividsantos.assembly.api.rest.v1.output;

import io.swagger.annotations.ApiModelProperty;

public class AgendaOutput {

    @ApiModelProperty(value = "Id of the created agenda.", example = "123")
    private Integer id;

    public AgendaOutput() {
    }

    public AgendaOutput(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
