package com.patriciasantos.desafio.models;

import java.util.ArrayList;
import java.util.List;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @NonNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    @Column(name = "status", nullable = false)
    @NonNull
    private Boolean status = true;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false, updatable = false)
    private Classroom classroom;

    @OneToMany(mappedBy = "task")
    private List<Grade> grades = new ArrayList<Grade>();

    public Task() {
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

    public Boolean isStatus() {
        return this.status;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Classroom getClassroom() {
        return this.classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Long getIdClassroom() {
        return this.classroom.getId();
    }

    public List<Grade> getGrades() {
        return this.grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public static class TaskBuilder {

        private Task task;

        public TaskBuilder create() {
            task = new Task();
            return this;
        }

        public TaskBuilder withName(final String name) {
            task.setName(name);
            return this;
        }

        public TaskBuilder withClassroom(final Classroom classroom) {
            task.setClassroom(classroom);
            return this;
        }

        public Task build() {
            return task;
        }
    }

}