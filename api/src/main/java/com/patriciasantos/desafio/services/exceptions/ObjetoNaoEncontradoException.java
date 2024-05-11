package com.patriciasantos.desafio.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.EntityNotFoundException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjetoNaoEncontradoException extends EntityNotFoundException {

    public ObjetoNaoEncontradoException(final String mensagem) {
        super(mensagem);
    }
}