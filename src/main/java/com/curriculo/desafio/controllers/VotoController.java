package com.curriculo.desafio.controllers;

import com.curriculo.desafio.controllers.domain.VotoRequest;
import com.curriculo.desafio.services.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.curriculo.desafio.entitys.Voto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/votos")
@RequiredArgsConstructor
@Tag(name = "Votos", description = "Endpoints para operações relacionadas a votos em pautas")
public class VotoController {

    private final VotoService votoService;

    @Operation(summary = "Registrar um novo voto para uma pauta")
    @PostMapping
    public ResponseEntity<Voto> registrarVoto(@RequestBody VotoRequest votoRequest) {
        Voto voto = votoService.registrarVoto(votoRequest.getPautaId(), votoRequest.getCpf(), votoRequest.getVoto());
        return ResponseEntity.status(HttpStatus.CREATED).body(voto);
    }

    @Operation(summary = "Obter o resultado paginado de votos para uma pauta")
    @GetMapping("/resultado")
    public ResponseEntity<Page<Map<String, Long>>> contarVotos(
            @Parameter(description = "ID da pauta para a qual obter o resultado dos votos", example = "1", required = true)
            @RequestParam Long pautaId,
            @Parameter(description = "Número da página (padrão: 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página (padrão: 10)", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Map<String, Long>> votos = votoService.contarVotos(pautaId, pageable);
        return ResponseEntity.ok(votos);
    }
}