package com.deividsantos.assembly.api.rest.v1.input;

import io.swagger.annotations.ApiModelProperty;

public class AssociatedInput {
    @ApiModelProperty(value = "CPF of the associated.")
    private String cpf;
    @ApiModelProperty(value = "Id of the associated.")
    private Integer id;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
