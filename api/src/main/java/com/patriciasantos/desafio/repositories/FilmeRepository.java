package com.patriciasantos.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patriciasantos.desafio.models.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    
}