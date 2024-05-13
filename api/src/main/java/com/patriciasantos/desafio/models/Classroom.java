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
@Table(name = "classroom")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    @NonNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    @Column(name = "status", nullable = false)
    @NonNull
    private Boolean status = true;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "classroom")
    private List<Student> students = new ArrayList<Student>();

    @OneToMany(mappedBy = "classroom")
    private List<Task> tasks = new ArrayList<Task>();

    public Classroom() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Boolean isStatus() {
        return this.status;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(final Boolean status) {
        this.status = status;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void setStudents(final List<Student> students) {
        this.students = students;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(final List<Task> tasks) {
        this.tasks = tasks;
    }

    public Long getIdUser() {
        return user.getId();
    }


    public static class ClassroomBuilder {

        private Classroom classroom;

        public ClassroomBuilder create() {
            classroom = new Classroom();
            return this;
        }

        public ClassroomBuilder withName(final String name) {
            classroom.setName(name);
            return this;
        }

        public ClassroomBuilder withUser(final User user) {
            classroom.setUser(user);
            return this;
        }

        public Classroom build() {
            return classroom;
        }
    }

}