package com.patriciasantos.desafio.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.patriciasantos.desafio.models.Voto;
import com.patriciasantos.desafio.models.to.VotoTO;
import com.patriciasantos.desafio.services.VotoService;

@RestController
@RequestMapping("/voto")
@Validated
public class VotoController {

    private final VotoService votoService;


    public VotoController(final VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping
    public ResponseEntity<Object> votar(@RequestBody VotoTO votoTO) {
        final Voto voto = this.votoService.votar(votoTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(voto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
