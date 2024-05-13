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

import com.patriciasantos.desafio.models.Student;
import com.patriciasantos.desafio.models.to.StudentTO;
import com.patriciasantos.desafio.services.StudentService;

@RestController
@RequestMapping("/student")
@Validated
public class StudentController {

    private final StudentService studentService;

    public StudentController(final StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/classroom/{classroomId}")
    public List<StudentTO> findAllByClassroom(@PathVariable final Long classroomId) {
        return this.studentService.findAllByClassroom(classroomId);
    }

    @GetMapping("/{id}")
    public StudentTO findById(@PathVariable final Long id) {
        return this.studentService.findTOById(id);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody StudentTO studentTO) {
        final Student student = this.studentService.create(studentTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(student.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody StudentTO studentTO, @PathVariable Long id) {
        this.studentService.update(studentTO, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        this.studentService.delete(id);
        return ResponseEntity.noContent().build();
    }


}