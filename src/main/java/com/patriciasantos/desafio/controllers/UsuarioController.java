package com.patriciasantos.desafio.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.patriciasantos.desafio.models.Usuario;
import com.patriciasantos.desafio.models.to.UsuarioTO;
import com.patriciasantos.desafio.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
@Validated
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody UsuarioTO usuarioTo) {
        final Usuario usuario = this.usuarioService.criar(usuarioTo);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody UsuarioTO usuarioTo, @PathVariable Long id) {
        this.usuarioService.atualizar(id, usuarioTo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        this.usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
    
}