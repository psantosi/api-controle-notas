package com.patriciasantos.desafio.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.patriciasantos.desafio.models.Filme;
import com.patriciasantos.desafio.models.to.FilmeTO;
import com.patriciasantos.desafio.models.view.FilmeView;
import com.patriciasantos.desafio.services.FilmeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/filme")

public class FilmeController {

    private final FilmeService filmeService;


    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @GetMapping
    public List<FilmeView> listar() {
        return this.filmeService.listar();
    }
    

    @GetMapping("/{id}")
    public FilmeView buscar(@PathVariable Long id) {
        return this.filmeService.buscarView(id);
    }

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody FilmeTO filmeTO) {
        final Filme filme = this.filmeService.criar(filmeTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(filme.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


}
