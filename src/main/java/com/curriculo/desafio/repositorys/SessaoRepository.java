package com.curriculo.desafio.repositorys;

import com.curriculo.desafio.entitys.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    List<Sessao> findByPautaId(Long pautaId);
}
