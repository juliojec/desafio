package com.curriculo.desafio.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.curriculo.desafio.entitys.Pauta;
import com.curriculo.desafio.entitys.Sessao;
import com.curriculo.desafio.repositorys.PautaRepository;
import com.curriculo.desafio.repositorys.SessaoRepository;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private SessaoService sessaoService;

    @Test
    public void testAbrirSessao() {
        Pauta pauta = Pauta.builder()
                .id(1L)
                .descricao("Pauta Teste")
                .build();

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        LocalDateTime fim = LocalDateTime.now().plusMinutes(30);

        Sessao sessaoSalva = Sessao.builder()
                .id(1L)
                .pauta(pauta)
                .inicio(LocalDateTime.now())
                .fim(fim)
                .build();

        when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessaoSalva);

        Sessao sessao = sessaoService.abrirSessao(1L, fim);

        assertNotNull(sessao);
        assertEquals(pauta.getId(), sessao.getPauta().getId());
    }

    @Test
    public void testListarSessoes() {
        List<Sessao> sessoes = new ArrayList<>();
        sessoes.add(Sessao.builder().build());
        sessoes.add(Sessao.builder().build());

        when(sessaoRepository.findByPautaId(1L)).thenReturn(sessoes);

        List<Sessao> sessoesRetornadas = sessaoService.listarSessoes(1L);

        assertNotNull(sessoesRetornadas);
        assertEquals(2, sessoesRetornadas.size());
    }
}
