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

import com.patriciasantos.desafio.models.Classroom;
import com.patriciasantos.desafio.models.to.ClassroomTO;
import com.patriciasantos.desafio.services.ClassroomService;

@RestController
@RequestMapping("/classroom")
@Validated
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(final ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public List<ClassroomTO> findAllByUser() {
        return this.classroomService.findAllByUser();
    }

    @GetMapping("/{id}")
    public ClassroomTO findById(@PathVariable final Long id) {
        return this.classroomService.findTOById(id);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ClassroomTO classroomTO) {
        final Classroom classroom = this.classroomService.create(classroomTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(classroom.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody ClassroomTO classroomTO, @PathVariable Long id) {
        this.classroomService.update(classroomTO, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        this.classroomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}