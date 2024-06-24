package com.curriculo.desafio.controllers.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotoRequest {

    private Long pautaId;
    private String cpf;
    private Boolean voto;

}