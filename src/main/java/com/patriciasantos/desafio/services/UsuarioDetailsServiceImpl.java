package com.patriciasantos.desafio.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.patriciasantos.desafio.models.Usuario;
import com.patriciasantos.desafio.repositories.UsuarioRepository;
import com.patriciasantos.desafio.security.UsuarioSpringSecurity;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;


    public UsuarioDetailsServiceImpl(final UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Usuario usuario = Optional.ofNullable(this.usuarioRepository.findByUsername(username)).orElseThrow(() -> new UsernameNotFoundException(" Usuário não encontrado"));
        return new UsuarioSpringSecurity(usuario.getId(), usuario.getUsername(), usuario.getPassword(), usuario.getPerfilEnum());    
    }
    
}
