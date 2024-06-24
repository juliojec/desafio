package com.curriculo.desafio.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.curriculo.desafio.entitys.Pauta;
import com.curriculo.desafio.repositorys.PautaRepository;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    @Test
    public void testCadastrarPauta() {
        Pauta pauta = Pauta.builder()
                .descricao("Nova Pauta")
                .build();
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);
        Pauta novaPauta = pautaService.cadastrarPauta(pauta);
        assertEquals("Nova Pauta", novaPauta.getDescricao());
    }

    @Test
    public void testListarPautas() {
        List<Pauta> pautas = new ArrayList<>();
        pautas.add(Pauta.builder().descricao("Pauta 1").build());
        pautas.add(Pauta.builder().descricao("Pauta 2").build());
        when(pautaRepository.findAll()).thenReturn(pautas);

        List<Pauta> pautasRetornadas = pautaService.listarPautas();

        assertEquals(2, pautasRetornadas.size());
    }
}
