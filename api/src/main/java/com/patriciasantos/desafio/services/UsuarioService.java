package com.patriciasantos.desafio.services;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.patriciasantos.desafio.models.Usuario;
import com.patriciasantos.desafio.models.enums.PerfilEnum;
import com.patriciasantos.desafio.models.to.UsuarioTO;
import com.patriciasantos.desafio.repositories.UsuarioRepository;
import com.patriciasantos.desafio.security.UsuarioSpringSecurity;
import com.patriciasantos.desafio.services.exceptions.AuthorizationException;
import com.patriciasantos.desafio.services.exceptions.ObjetoNaoEncontradoException;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
   
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsuarioService(final UsuarioRepository usuarioRepository,
                          final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Usuario obter(final Long id) {
        if (!this.isUsuarioAdmin() && !this.usuarioAutenticado().getId().equals(id)) {
            throw new AuthorizationException("Acesso negado");
        }

        final Usuario usuario = this.usuarioRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Usuário não encontrado."));
        this.validarSeUsuarioEstaAtivo(usuario);
        return usuario;
    }

    public Usuario obterUsuarioLogado() {
        final UsuarioSpringSecurity usuarioSpringSecurity = this.usuarioAutenticado();
        return this.obter(usuarioSpringSecurity.getId());
    }

    public Long obterUsuarioIdLogado() {
        final UsuarioSpringSecurity usuarioSpringSecurity = this.usuarioAutenticado();
        return usuarioSpringSecurity.getId();
    }


    @Transactional(rollbackOn = Exception.class)
    public Usuario criar(final UsuarioTO usuarioTO) {        
        final Usuario usuario = new Usuario.UsuarioBuilder().create()
        .comUsername(usuarioTO.getUsername())
        .comPassword(this.bCryptPasswordEncoder.encode(usuarioTO.getPassword()))
        .comPerfil(usuarioTO.getPerfil())
        .build();
        
        return this.usuarioRepository.save(usuario);
    }

    @Transactional(rollbackOn = Exception.class)
    public void atualizar(final Long id, final UsuarioTO usuarioTO) {
        final Usuario usuario = this.obter(id);
        usuario.setUsername(usuarioTO.getUsername());
        usuario.setPassword(this.bCryptPasswordEncoder.encode(usuarioTO.getPassword()));
        this.usuarioRepository.save(usuario);
    }

    @Transactional(rollbackOn = Exception.class)
    public void excluir(final Long id) {
        final Usuario usuario = this.obter(id);
        usuario.setAtivo(false);
        this.usuarioRepository.save(usuario);
    }

    public void validarSeUsuarioEstaAtivo(final Usuario usuario) {
        if (!usuario.getAtivo()) {
            throw new ObjetoNaoEncontradoException("Usuário excluido.");
        }
    }

    public boolean isUsuarioAdmin() {
        final UsuarioSpringSecurity usuario = Optional.ofNullable(this.usuarioAutenticado()).orElseThrow(() -> new AuthorizationException("Acesso negado!"));
        return usuario.hasRole(PerfilEnum.ADMIN);
    }

    public UsuarioSpringSecurity usuarioAutenticado() {
        try {
            return (UsuarioSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
    
}