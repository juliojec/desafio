package com.curriculo.desafio.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curriculo.desafio.entitys.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
	
}