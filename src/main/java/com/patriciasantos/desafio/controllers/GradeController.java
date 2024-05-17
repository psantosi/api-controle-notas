package com.patriciasantos.desafio.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patriciasantos.desafio.models.to.GradeTO;
import com.patriciasantos.desafio.services.GradeService;

@RestController
@RequestMapping("/grade")
@Validated
public class GradeController {

    private final GradeService gradeService;

    public GradeController(final GradeService gradeService) {
        this.gradeService = gradeService;
    }


    @GetMapping("/student/{studentId}")
    public List<GradeTO> findAllByClassroom(@PathVariable final Long studentId) {
        return this.gradeService.findAllByStudent(studentId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody GradeTO gradeTO, @PathVariable Long id) {
        this.gradeService.update(gradeTO, id);
        return ResponseEntity.noContent().build();
    }
}