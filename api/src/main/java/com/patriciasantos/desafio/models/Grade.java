package com.patriciasantos.desafio.models;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "grade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "grade", length = 10, nullable = false)
    @NonNull
    private Integer grade = 0;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false, updatable = false)
    private Classroom classroom;    

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false, updatable = false)
    private Task task;

    public Grade() {

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

    public void setClassroom(final Classroom classroom) {
        this.classroom = classroom;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(final Student student) {
        this.student = student;
    }

    public Task getTask() {
        return this.task;
    }

    public void setTask(final Task task) {
        this.task = task;
    }

    public static class GradeBuilder {

        private Grade grade;

        public GradeBuilder create() {
            grade = new Grade();
            return this;
        }

        public GradeBuilder withClassroom(final Classroom classroom) {
            grade.setClassroom(classroom);
            return this;
        }


        public GradeBuilder withStudent(final Student student) {
            grade.setStudent(student);
            return this;
        }

        public GradeBuilder withTask(final Task task) {
            grade.setTask(task);
            return this;
        }

        public Grade build() {
            return grade;
        }
    }

}
