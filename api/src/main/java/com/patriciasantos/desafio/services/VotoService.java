package com.patriciasantos.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.patriciasantos.desafio.models.Filme;
import com.patriciasantos.desafio.models.Usuario;
import com.patriciasantos.desafio.models.Voto;
import com.patriciasantos.desafio.models.to.VotoTO;
import com.patriciasantos.desafio.repositories.VotoRepository;
import com.patriciasantos.desafio.services.exceptions.AuthorizationException;

import jakarta.transaction.Transactional;

@Service
public class VotoService {

    private final VotoRepository votoRepository;
    private final UsuarioService usuarioService;
    private final FilmeService filmeService;


    public VotoService(final VotoRepository votoRepository, final UsuarioService usuarioService, FilmeService filmeService) {
        this.votoRepository = votoRepository;
        this.usuarioService = usuarioService;
        this.filmeService = filmeService;
    }

    @Transactional(rollbackOn = Exception.class)
    public Voto votar(final VotoTO votoTO) {
        if (this.usuarioService.isUsuarioAdmin()) {
            throw new AuthorizationException("Você não tem permissão para voltar nos filmes.");
        }

        final Usuario usuario = this.usuarioService.obterUsuarioLogado();
        final Filme filme = this.filmeService.buscar(votoTO.getIdFilme());
        final List<Voto> votos = this.votoRepository.findByUsuarioId(usuario.getId());
        final Optional<Voto> votoDoFilme = votos.stream().filter(v -> v.getFilme().getId().equals(filme.getId())).findFirst();
        Voto voto = null;

        if (votoDoFilme.isPresent()) {
            voto = votoDoFilme.get();
            voto.setNota(votoTO.getNota());
        } else {
            voto = new Voto.VotoBuilder().create()
                    .comNota(votoTO.getNota())
                    .comUsuario(usuario.getId())
                    .comFilme(filme)
                    .build();
        }

        return this.votoRepository.save(voto);
    }
    
}
