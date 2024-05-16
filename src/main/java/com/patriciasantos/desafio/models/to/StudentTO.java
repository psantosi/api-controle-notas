package com.patriciasantos.desafio.models.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.patriciasantos.desafio.models.Student;

public class StudentTO implements Serializable {

    private Long id;
    private String name;
    private Long classroomId;
    private List<GradeTO> grades = new ArrayList<GradeTO>(); 


    public StudentTO() {
    }

    public StudentTO(final Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.classroomId = student.getIdClassroom();
        this.grades.addAll(student.getGrades()
        .stream()
        .map(grade -> new GradeTO(grade.getId(), grade.getGrade()))
        .toList());
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

    public List<GradeTO> getGrades() {
        return this.grades;
    }

    public void setGrades(List<GradeTO> grades) {
        this.grades = grades;
    }

    public void addGrades(final List<GradeTO> gradeTOs) {
        this.grades.addAll(gradeTOs);
    }

}