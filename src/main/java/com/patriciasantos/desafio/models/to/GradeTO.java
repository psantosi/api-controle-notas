package com.patriciasantos.desafio.models.to;

import java.io.Serializable;

import com.patriciasantos.desafio.models.Classroom;
import com.patriciasantos.desafio.models.Grade;
import com.patriciasantos.desafio.models.Student;
import com.patriciasantos.desafio.models.Task;

public class GradeTO implements Serializable{
    
    private Long id;
    private Integer grade;
    private Classroom classroom;    
    private Student student;
    private Task task;    

    public GradeTO() {
    }

    public GradeTO(final Grade grade) {
        this.id = grade.getId();
        this.grade = grade.getGrade();
        this.classroom = grade.getClassroom();
        this.student = grade.getStudent();
        this.task = grade.getTask();
    }

    public GradeTO(Long id, Integer grade) {
        this.id = id;
        this.grade = grade;
    }
    
    public GradeTO(final Classroom classroom, final Student student, final Task task) {
        this.classroom = classroom;
        this.student = student;
        this.task = task;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Integer getGrade() {
        return this.grade;
    }

    public void setGrade(final Integer grade) {
        this.grade = grade;
    }


    public Classroom getClassroom() {
        return this.classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
    

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Task getTask() {
        return this.task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    
}