package com.curriculo.desafio.controllers;

import com.curriculo.desafio.entitys.Pauta;
import com.curriculo.desafio.services.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pautas")
@RequiredArgsConstructor
@Tag(name = "Pautas", description = "Endpoints para operações relacionadas a pautas")
public class PautaController {

    private final PautaService pautaService;

    @Operation(summary = "Cadastrar uma nova pauta")
    @PostMapping
    public ResponseEntity<Pauta> criarPauta(@RequestBody Pauta pauta) {
        Pauta novaPauta = pautaService.cadastrarPauta(pauta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPauta);
    }

    @Operation(summary = "Listar pautas cadastradas")
    @GetMapping
    public ResponseEntity<List<Pauta>> listarPautas() {
        return ResponseEntity.ok(pautaService.listarPautas());
    }

    @Operation(summary = "Buscar uma pauta pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscarPautaPorId(
            @Parameter(description = "ID da pauta a ser buscada", example = "1", required = true)
            @PathVariable Long id) {
        Pauta pauta = pautaService.buscarPorId(id);
        return ResponseEntity.ok(pauta);
    }

}