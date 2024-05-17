package com.patriciasantos.desafio.models.to;

import java.io.Serializable;

import com.patriciasantos.desafio.models.Classroom;
import com.patriciasantos.desafio.models.Student;
import com.patriciasantos.desafio.models.Task;

public class GradeTO implements Serializable{
    
    private Long id;
    private Integer grade;
    private Classroom classroom;    
    private Student student;
    private Task task;
    private TaskTO taskObj;    

    public GradeTO() {
    }

    public GradeTO(final Long id, final Integer grade, final Task task) {
        this.id = id;
        this.grade = grade;
        this.taskObj = new TaskTO(task);
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


    public TaskTO getTaskObj() {
        return this.taskObj;
    }

    public void setTaskObj(TaskTO taskObj) {
        this.taskObj = taskObj;
    }
        
}