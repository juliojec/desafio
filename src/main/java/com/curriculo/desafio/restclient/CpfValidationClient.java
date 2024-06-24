package com.curriculo.desafio.restclient;

import com.curriculo.desafio.restclient.domain.CpfValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cpfValidationClient", url = "${validators.cpf.api.url}")
public interface CpfValidationClient {

    @GetMapping("/validator")
    CpfValidationResponse validateCpf(
            @RequestParam("value") String cpf,
            @RequestParam("type") String type,
            @RequestParam("token") String token
    );

}