package com.patriciasantos.desafio.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.patriciasantos.desafio.models.Task;
import com.patriciasantos.desafio.models.to.TaskTO;
import com.patriciasantos.desafio.services.TaskService;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    private final TaskService taskService;

    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/classroom/{classroomId}")
    public List<TaskTO> findAllByClassroom(@PathVariable final Long classroomId) {
        return this.taskService.findAllByClassroom(classroomId);
    }

    @GetMapping("/{id}")
    public TaskTO findById(@PathVariable final Long id) {
        return this.taskService.findTOById(id);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody TaskTO taskTO) {
        final Task classroom = this.taskService.create(taskTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(classroom.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody TaskTO taskTO, @PathVariable Long id) {
        this.taskService.update(taskTO, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}