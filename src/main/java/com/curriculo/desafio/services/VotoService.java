package com.curriculo.desafio.services;

import com.curriculo.desafio.config.exceptions.VotoException;
import com.curriculo.desafio.entitys.Pauta;
import com.curriculo.desafio.repositorys.PautaRepository;
import com.curriculo.desafio.restclient.CpfValidationClient;
import com.curriculo.desafio.restclient.domain.CpfValidationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.curriculo.desafio.entitys.Voto;
import com.curriculo.desafio.repositorys.VotoRepository;
import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;

    private final PautaRepository pautaRepository;

    private final SessaoService sessaoService;

    private final CpfValidationClient cpfValidationClient;

    @Value("${validators.cpf.api.token}")
    private String cpfValidationToken;

    public Voto registrarVoto(Long pautaId, String cpf, Boolean voto) {
        votoRepository.findByPautaIdAndCpf(pautaId, cpf)
                .ifPresent(v -> {throw new VotoException("Associado já votou nesta pauta");});

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new VotoException("Pauta não encontrada"));

        if (!sessaoService.isSessaoAberta(pauta))
            throw new VotoException("Não existe sessão aberta para esta pauta.");

        boolean cpfValido = validarCpfParaVotacao(cpf);
        if (!cpfValido)
            throw new VotoException("CPF do associado inválido ou não pode votar.");

        Voto novoVoto = Voto.builder()
                .pauta(pauta)
                .cpf(cpf)
                .voto(voto)
                .build();

        return votoRepository.save(novoVoto);
    }

    public Page<Map<String, Long>> contarVotos(Long pautaId, Pageable pageable) {
        List<Voto> votos = votoRepository.findByPautaId(pautaId, pageable);
        long total = votoRepository.countByPautaId(pautaId); // Total de registros

        long votosSim = votos.stream().filter(Voto::getVoto).count();
        long votosNao = votos.stream().filter(v -> !v.getVoto()).count();

        List<Map<String, Long>> resultados = List.of(
                Map.of("Sim", votosSim),
                Map.of("Não", votosNao)
        );

        return new PageImpl<>(resultados, pageable, total);
    }

    public boolean validarCpfParaVotacao(String cpf) {
        try {
            CpfValidationResponse response = cpfValidationClient.validateCpf(cpf, "cpf", cpfValidationToken);
            return response.isValid();
        } catch (Exception e) {
            log.error("Erro ao validar CPF {} para votação: {}", cpf, e.getMessage());
            return false;
        }
    }
}