package com.curriculo.desafio.services;

import com.curriculo.desafio.config.exceptions.PautaException;
import org.springframework.stereotype.Service;

import com.curriculo.desafio.entitys.Pauta;
import com.curriculo.desafio.repositorys.PautaRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;

    public Pauta cadastrarPauta(Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    public List<Pauta> listarPautas() {
        return pautaRepository.findAll();
    }

    public Pauta buscarPorId(Long id) {
        return pautaRepository.findById(id).orElseThrow(() -> new PautaException("Pauta não encontrada"));
    }
    
}