package com.patriciasantos.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patriciasantos.desafio.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsername(final String username);
}