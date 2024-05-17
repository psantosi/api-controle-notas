package com.patriciasantos.desafio.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.patriciasantos.desafio.models.Grade;
import com.patriciasantos.desafio.models.to.GradeTO;
import com.patriciasantos.desafio.repositories.GradeRepository;
import com.patriciasantos.desafio.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;

    public GradeService(final GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public List<GradeTO> findAllByStudent(final Long studentId) {
        final List<Grade> grades = this.gradeRepository.findByStudentId(studentId);
        return grades.stream()
        .map(grade -> new GradeTO(grade.getId(), grade.getGrade(), grade.getTask()))
        .toList();            
    }

    public Grade findById(final Long id) {
        final Grade grade = this.gradeRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Nota não encontrado!"));
        return grade;
    }

    @Transactional(rollbackOn = Exception.class)
    public void createAll(final List<GradeTO> gradeTOs) {
        final List<Grade> grades = new ArrayList<Grade>();

        gradeTOs.forEach(gradeTO -> {
            final Grade grade = new Grade.GradeBuilder().create()
            .withClassroom(gradeTO.getClassroom())
            .withStudent(gradeTO.getStudent())
            .withTask(gradeTO.getTask())
            .build();

            grades.add(grade);
        });

        this.gradeRepository.saveAll(grades);
    }

    @Transactional(rollbackOn = Exception.class)
    public void update(final GradeTO gradeTO, final Long id) {
        final Grade grade = this.findById(id);
        grade.setGrade(gradeTO.getGrade());
        this.gradeRepository.save(grade);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteByStudent(final Long studantId) {
        final List<Grade> grades = this.gradeRepository.findByStudentId(studantId);
        this.gradeRepository.deleteAll(grades);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteByTask(final Long taskId) {
        final List<Grade> grades = this.gradeRepository.findByTaskId(taskId);
        this.gradeRepository.deleteAll(grades);
    }

}