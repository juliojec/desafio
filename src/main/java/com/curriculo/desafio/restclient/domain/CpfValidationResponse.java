package com.curriculo.desafio.restclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CpfValidationResponse {

    private boolean valid;
    private String formatted;

}