package com.patriciasantos.desafio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.patriciasantos.desafio.models.Classroom;
import com.patriciasantos.desafio.models.Grade;
import com.patriciasantos.desafio.models.Student;
import com.patriciasantos.desafio.models.Task;
import com.patriciasantos.desafio.models.to.GradeTO;
import com.patriciasantos.desafio.models.to.StudentTO;
import com.patriciasantos.desafio.repositories.StudentRepository;
import com.patriciasantos.desafio.repositories.TaskRepository;
import com.patriciasantos.desafio.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ClassroomService classroomService;
    private final TaskRepository taskRepository;
    private final GradeService gradeService;

    public StudentService(final StudentRepository studentRepository,
                          final ClassroomService classroomService,
                          final TaskRepository taskRepository,
                          final GradeService gradeService) {
        this.studentRepository = studentRepository;
        this.classroomService = classroomService;
        this.taskRepository = taskRepository;
        this.gradeService = gradeService;
    }

    public List<StudentTO> findAllByClassroom(final Long classroomId) {
        final List<StudentTO> studentTOs = this.studentRepository.findByClassroomId(classroomId)
        .stream()
        .filter(student -> student.isStatus())
        .map(student -> new StudentTO(student))
        .toList();

        this.addGrades(studentTOs, classroomId);

        return studentTOs;
    }

    public Student findById(final Long id) {
        final Student student = this.studentRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Aluno não encontrado!"));

        this.validateIfStudentWasDeleted(student);
        return student;
    }

    public StudentTO findTOById(final Long id) {
        return new StudentTO(this.findById(id));
    }

    @Transactional(rollbackOn = Exception.class)
    public Student create(final StudentTO studentTO) {
        final Classroom classroom = this.classroomService.findById(studentTO.getClassroomId());
        final Student student = new Student.StudentBuilder().create()
        .withName(studentTO.getName())
        .withClassroom(classroom)
        .build();

        this.studentRepository.save(student);
        this.createGrades(student, classroom);

        return student;
    }

    @Transactional(rollbackOn = Exception.class)
    public void update(final StudentTO studentTO, final Long id) {
        final Student student = this.findById(id);
        student.setName(studentTO.getName());
        this.studentRepository.save(student);
    }

    @Transactional(rollbackOn = Exception.class)
    public void delete(final Long id) {
        final Student student = this.findById(id);
        student.setStatus(false);
        this.studentRepository.save(student);
        this.gradeService.deleteByStudent(id);
    }

    private void addGrades(final List<StudentTO> studentTOs, final Long classroomId) {
        final List<Grade> grades = this.gradeService.findAllByClassroom(classroomId);
        if (!grades.isEmpty()) {
            studentTOs.forEach(studentTO -> {
                final List<GradeTO> gradeTOs = grades
                .stream()
                .filter(grade -> grade.getStudent().getId().equals(studentTO.getId()))
                .map(grade -> new GradeTO(grade))
                .toList();

                studentTO.addGrades(gradeTOs);
            });
        }
    }

    private void createGrades(final Student student, final Classroom classroom) {
        final List<Task> tasks = this.taskRepository.findByClassroomId(classroom.getId()).stream().filter(task -> task.isStatus()).toList();
        
        if (!tasks.isEmpty()) {
            final List<GradeTO> gradeTOs = tasks.stream().map(task -> new GradeTO(classroom, student, task)).toList();
            this.gradeService.createAll(gradeTOs);
        }
    }

    private void validateIfStudentWasDeleted(final Student student) {
        if (!student.isStatus()) {
            throw new ObjectNotFoundException("Aluno excluido!");
        }
    }
}