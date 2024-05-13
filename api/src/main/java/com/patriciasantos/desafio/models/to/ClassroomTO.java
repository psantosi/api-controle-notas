package com.patriciasantos.desafio.models.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.patriciasantos.desafio.models.Classroom;
import com.patriciasantos.desafio.models.Student;
import com.patriciasantos.desafio.models.Task;

public class ClassroomTO implements Serializable {

    private Long id;
    private String name;
    private List<Student> students = new ArrayList<Student>();
    private List<Task> tasks = new ArrayList<Task>();

    public ClassroomTO() {
    }


    public ClassroomTO(final Classroom classroom) {
        this.id = classroom.getId();
        this.name = classroom.getName();
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

    public List<Student> getStudents() {
        return this.students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }    

}