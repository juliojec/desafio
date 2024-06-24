package com.curriculo.desafio.repositorys;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curriculo.desafio.entitys.Voto;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    List<Voto> findByPautaId(Long pautaId, Pageable pageable);

    Optional<Voto> findByPautaIdAndCpf(Long pautaId, String cpf);

    long countByPautaId(Long pautaId);

}