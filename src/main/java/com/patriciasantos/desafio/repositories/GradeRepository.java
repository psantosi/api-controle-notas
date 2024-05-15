package com.patriciasantos.desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patriciasantos.desafio.models.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findByClassroomId(final Long id);
    List<Grade> findByStudentId(final Long id);
    List<Grade> findByTaskId(final Long id);
}
