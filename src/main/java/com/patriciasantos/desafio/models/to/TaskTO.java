package com.patriciasantos.desafio.models.to;

import java.io.Serializable;

import com.patriciasantos.desafio.models.Task;

public class TaskTO implements Serializable {

    private Long id;
    private String name;
    private Long classroomId;


    public TaskTO() {
    }


    public TaskTO(final Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.classroomId = task.getIdClassroom();
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