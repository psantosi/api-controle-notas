package com.patriciasantos.desafio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.patriciasantos.desafio.models.Classroom;
import com.patriciasantos.desafio.models.Student;
import com.patriciasantos.desafio.models.to.StudentTO;
import com.patriciasantos.desafio.repositories.StudentRepository;
import com.patriciasantos.desafio.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ClassroomService classroomService;

    public StudentService(final StudentRepository studentRepository, final ClassroomService classroomService) {
        this.studentRepository = studentRepository;
        this.classroomService = classroomService;
    }

    public List<StudentTO> findAllByClassroom(final Long classroomId) {
        final List<Student> students = this.studentRepository.findByClassroomId(classroomId);
        return students.stream().map(student -> new StudentTO(student)).toList();
    }

    public Student findById(final Long id) {
        final Student student = this.studentRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Tarefa não encontrada!"));

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

        return this.studentRepository.save(student);
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
    }

    private void validateIfStudentWasDeleted(final Student student) {
        if (!student.isStatus()) {
            throw new ObjectNotFoundException("Aluno excluido!");
        }
    }
}