package com.patriciasantos.desafio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.patriciasantos.desafio.models.Classroom;
import com.patriciasantos.desafio.models.Task;
import com.patriciasantos.desafio.models.to.TaskTO;
import com.patriciasantos.desafio.repositories.TaskRepository;
import com.patriciasantos.desafio.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ClassroomService classroomService;
    
    public TaskService(final TaskRepository taskRepository, final ClassroomService classroomService) {
        this.taskRepository = taskRepository;
        this.classroomService = classroomService;
    }

    public List<TaskTO> findAllByClassroom(final Long classroomId) {
        final List<Task> tasks = this.taskRepository.findByClassroomId(classroomId);
        return tasks.stream().map(task -> new TaskTO(task)).toList();
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

        return this.taskRepository.save(task);
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
    }

    private void validateIfTaskWasDeleted(final Task task) {
        if (!task.isStatus()) {
            throw new ObjectNotFoundException("Tarefa excluida!");
        }
    }
}