package com.patriciasantos.desafio.models.to;

import java.io.Serializable;

import com.patriciasantos.desafio.models.Student;

public class StudentTO implements Serializable {

    private Long id;
    private String name;
    private Long classroomId;


    public StudentTO() {
    }


    public StudentTO(final Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.classroomId = student.getIdClassroom();
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


}