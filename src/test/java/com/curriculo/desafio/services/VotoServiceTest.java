package com.curriculo.desafio.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.curriculo.desafio.entitys.Pauta;
import com.curriculo.desafio.entitys.Voto;
import com.curriculo.desafio.repositorys.PautaRepository;
import com.curriculo.desafio.repositorys.VotoRepository;
import com.curriculo.desafio.restclient.CpfValidationClient;
import com.curriculo.desafio.restclient.domain.CpfValidationResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private SessaoService sessaoService;

    @Mock
    private CpfValidationClient cpfValidationClient;

    @InjectMocks
    private VotoService votoService;

    @Test
    public void testRegistrarVoto() {
        Long pautaId = 1L;
        String cpf = "12345678900";
        Boolean voto = true;

        Pauta pauta = Pauta.builder().id(pautaId).build();
        Voto novoVoto = Voto.builder().cpf(cpf).voto(voto).pauta(pauta).build();
        when(sessaoService.isSessaoAberta(pauta)).thenReturn(true);
        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(votoRepository.findByPautaIdAndCpf(pautaId, cpf)).thenReturn(Optional.empty());
        when(votoRepository.save(any(Voto.class))).thenReturn(novoVoto);

        CpfValidationResponse response = new CpfValidationResponse(true, "000.000.00-00");
        given(cpfValidationClient.validateCpf(eq("12345678900"), eq("cpf"), eq(null)))
                .willReturn(response);

        Voto registraVoto = votoService.registrarVoto(pautaId, cpf, voto);

        assertNotNull(registraVoto);
        assertEquals(pautaId, registraVoto.getPauta().getId());
        assertEquals(cpf, registraVoto.getCpf());
        assertEquals(voto, registraVoto.getVoto());
    }

    @Test
    public void testContarVotos() {
        Long pautaId = 1L;
        int page = 0;
        int size = 10;

        List<Voto> votos = new ArrayList<>();
        votos.add(Voto.builder().voto(true).build());
        votos.add(Voto.builder().voto(false).build());

        when(votoRepository.findByPautaId(pautaId, PageRequest.of(page, size))).thenReturn(votos);
        when(votoRepository.countByPautaId(pautaId)).thenReturn((long) votos.size());

        Page<Map<String, Long>> resultados = votoService.contarVotos(pautaId, PageRequest.of(page, size));

        assertNotNull(resultados);
        assertEquals(2, resultados.getTotalElements());

        Map<String, Long> resultadoSim = resultados.getContent().get(0);
        Map<String, Long> resultadoNao = resultados.getContent().get(1);

        assertEquals(1L, resultadoSim.get("Sim"));
        assertEquals(1L, resultadoNao.get("Não"));
    }
}
