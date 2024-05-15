package com.patriciasantos.desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patriciasantos.desafio.models.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByClassroomId(final Long id);
}