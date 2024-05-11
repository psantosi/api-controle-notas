package com.patriciasantos.desafio.repositories;

import org.springframework.stereotype.Repository;

import com.patriciasantos.desafio.models.Voto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long>  {
    List<Voto> findByUsuarioId(Long id);
}
