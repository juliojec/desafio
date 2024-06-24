package com.curriculo.desafio.controllers;

import com.curriculo.desafio.services.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.curriculo.desafio.entitys.Sessao;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sessoes")
@RequiredArgsConstructor
@Tag(name = "Sessões", description = "Endpoints para operações relacionadas a sessões de votação")
public class SessaoController {

    private final SessaoService sessaoService;

    @Operation(summary = "Abrir uma nova sessão de votação para uma pauta")
    @PostMapping
    public ResponseEntity<Sessao> abrirSessao(
            @Parameter(description = "ID da pauta para a qual abrir a sessão", example = "1", required = true)
            @RequestParam Long pautaId,
            @Parameter(description = "Duração em minutos da sessão (padrão: 1 minuto)", example = "10")
            @RequestParam(defaultValue = "1") long duracaoMinutos) {
        LocalDateTime fim = LocalDateTime.now().plusMinutes(duracaoMinutos);
        Sessao sessao = sessaoService.abrirSessao(pautaId, fim);
        return ResponseEntity.status(HttpStatus.CREATED).body(sessao);
    }


    @Operation(summary = "Listar todas as sessões de votação para uma pauta")
    @GetMapping
    public ResponseEntity<List<Sessao>> listarSessoes(
            @Parameter(description = "ID da pauta para a qual listar as sessões", example = "1", required = true)
            @RequestParam Long pautaId) {
        return ResponseEntity.ok(sessaoService.listarSessoes(pautaId));
    }

}