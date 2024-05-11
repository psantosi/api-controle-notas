package com.patriciasantos.desafio.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.patriciasantos.desafio.models.Filme;
import com.patriciasantos.desafio.models.to.FilmeTO;
import com.patriciasantos.desafio.models.view.FilmeView;
import com.patriciasantos.desafio.repositories.FilmeRepository;
import com.patriciasantos.desafio.services.exceptions.AuthorizationException;
import com.patriciasantos.desafio.services.exceptions.ObjetoNaoEncontradoException;

import jakarta.transaction.Transactional;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final UsuarioService usuarioService;


    public FilmeService(final FilmeRepository filmeRepository, final UsuarioService usuarioService) {
        this.filmeRepository = filmeRepository;
        this.usuarioService = usuarioService;
    }

    public List<FilmeView> listar() {
        final List<Filme> filmes = this.filmeRepository.findAll();
        final Long usuarioId = this.usuarioService.obterUsuarioIdLogado();
        return this.converterParaView(filmes, usuarioId);
    }

    public FilmeView buscarView(final Long id) {
        final Filme filme = this.filmeRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Filme não encontrado."));
        final Long usuarioId = this.usuarioService.obterUsuarioIdLogado();
        return FilmeView.toView(filme, usuarioId);
    }

    public Filme buscar(final Long id) {
        return this.filmeRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Filme não encontrado."));
    }

    private List<FilmeView> converterParaView(final List<Filme> filmes, final Long usuarioId) {
        final List<FilmeView> views = new ArrayList<>();
        filmes.forEach(filme -> views.add(FilmeView.toView(filme, usuarioId)));
        return views;
    }

    @Transactional(rollbackOn = Exception.class)
    public Filme criar(final FilmeTO filmeTO) {
        if (!this.usuarioService.isUsuarioAdmin()) {
            throw new AuthorizationException("Você não tem permissão para cadastrar um filme.");
        }

        final Filme filme = new Filme.FilmeBuilder().create()
        .comTitulo(filmeTO.getTitulo())
        .comDiretor(filmeTO.getDiretor())
        .comGenero(filmeTO.getGenero())
        .comDescricao(filmeTO.getDescricao())
        .build();

                
        return this.filmeRepository.save(filme);
    }

}
