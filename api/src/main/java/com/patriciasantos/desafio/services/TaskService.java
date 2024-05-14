package com.patriciasantos.desafio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.patriciasantos.desafio.models.Classroom;
import com.patriciasantos.desafio.models.Student;
import com.patriciasantos.desafio.models.Task;
import com.patriciasantos.desafio.models.to.GradeTO;
import com.patriciasantos.desafio.models.to.TaskTO;
import com.patriciasantos.desafio.repositories.StudentRepository;
import com.patriciasantos.desafio.repositories.TaskRepository;
import com.patriciasantos.desafio.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ClassroomService classroomService;
    private final StudentRepository studentRepository;
    private final GradeService gradeService;
    
    public TaskService(final TaskRepository taskRepository,
                       final ClassroomService classroomService,
                       final StudentRepository studentRepository, 
                       final GradeService gradeService) {
        this.taskRepository = taskRepository;
        this.classroomService = classroomService;
        this.studentRepository = studentRepository;
        this.gradeService = gradeService;
    }

    public List<TaskTO> findAllByClassroom(final Long classroomId) {
        return this.taskRepository.findByClassroomId(classroomId)
        .stream()
        .filter(task -> task.isStatus())
        .map(task -> new TaskTO(task))
        .toList();
    }

    public Task findById(final Long id) {
        final Task task = this.taskRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Tarefa não encontrada!"));

        this.validateIfTaskWasDeleted(task);
        return task;
     }

     public TaskTO findTOById(final Long id) {
        return new TaskTO(this.findById(id));
     }

    @Transactional(rollbackOn = Exception.class)
    public Task create(final TaskTO taskTO) {
        final Classroom classroom = this.classroomService.findById(taskTO.getClassroomId());
        final Task task = new Task.TaskBuilder().create()
        .withName(taskTO.getName())
        .withClassroom(classroom)
        .build();

        this.taskRepository.save(task);
        this.createGrades(task, classroom);

        return task;
    }

    @Transactional(rollbackOn = Exception.class)
    public void update(final TaskTO taskTO, final Long id) {
        final Task task = this.findById(id);
        task.setName(taskTO.getName());
        this.taskRepository.save(task);
    }
    
    @Transactional(rollbackOn = Exception.class)
    public void delete(final Long id) {
        final Task task = this.findById(id);
        task.setStatus(false);
        this.taskRepository.save(task);
        this.gradeService.deleteByTask(id);
    }

    private void createGrades(final Task task, final Classroom classroom) {
        final List<Student> students = this.studentRepository.findByClassroomId(classroom.getId());
        
        if (!students.isEmpty()) {
            final List<GradeTO> gradeTOs = students.stream().map(student -> new GradeTO(classroom, student, task)).toList();
            this.gradeService.createAll(gradeTOs);
        }
    }

    private void validateIfTaskWasDeleted(final Task task) {
        if (!task.isStatus()) {
            throw new ObjectNotFoundException("Tarefa excluida!");
        }
    }
}