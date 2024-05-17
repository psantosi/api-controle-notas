package com.patriciasantos.desafio.models.to;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.patriciasantos.desafio.models.Grade;
import com.patriciasantos.desafio.models.Student;

public class StudentTO implements Serializable {

    private Long id;
    private String name;
    private Long classroomId;
    private List<Grade> grades;


    public StudentTO() {
    }

    public StudentTO(final Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.classroomId = student.getIdClassroom();
        this.grades = student.getGrades();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getClassroomId() {
        return this.classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public BigDecimal getAverage() {
        final BigDecimal sumGrades = this.grades.stream()
        .map(Grade::getGrade)
        .map(BigDecimal::valueOf)
        .reduce(BigDecimal.ZERO, BigDecimal::add);


        if (sumGrades.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        final Integer numTask = this.grades.stream()
        .map(Grade::getTask)
        .toList().size();

        return sumGrades.divide(BigDecimal.valueOf(numTask), 0, RoundingMode.HALF_UP);
    }
}