package com.curriculo.desafio.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.curriculo.desafio.config.exceptions.PautaException;
import com.curriculo.desafio.entitys.Pauta;
import com.curriculo.desafio.repositorys.PautaRepository;
import org.springframework.stereotype.Service;

import com.curriculo.desafio.entitys.Sessao;
import com.curriculo.desafio.repositorys.SessaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;

    private final PautaRepository pautaRepository;

    public Sessao abrirSessao(Long idPauta, LocalDateTime fim) {
        Pauta pauta = pautaRepository.findById(idPauta)
                .orElseThrow(() -> new PautaException("Pauta não encontrada"));

        Sessao sessao = Sessao.builder()
                .inicio(LocalDateTime.now())
                .pauta(pauta)
                .fim(fim)
                .build();

        return sessaoRepository.save(sessao);
    }

    public List<Sessao> listarSessoes(Long pautaId) {
        return sessaoRepository.findByPautaId(pautaId);
    }

    public boolean isSessaoAberta(Pauta pauta) {
        LocalDateTime now = LocalDateTime.now();
        return sessaoRepository.findByPautaAndFimAfter(pauta, now).stream()
                .anyMatch(sessao -> sessao.getFim().isAfter(now));
    }
}