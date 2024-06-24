package com.curriculo.desafio.repositorys;

import com.curriculo.desafio.entitys.Pauta;
import com.curriculo.desafio.entitys.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    List<Sessao> findByPautaId(Long pautaId);
    List<Sessao> findByPautaAndFimAfter(Pauta pauta, LocalDateTime now);
}
