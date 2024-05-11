package com.patriciasantos.desafio.security;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.patriciasantos.desafio.models.enums.PerfilEnum;

public class UsuarioSpringSecurity implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public UsuarioSpringSecurity(Long id, String username, String password, PerfilEnum perfilEnum) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = Set.of(new SimpleGrantedAuthority(perfilEnum.getDescricao()));
    }

    public Long getId() {
        return id;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(final PerfilEnum perfilEnum) {
        return getAuthorities().contains(new SimpleGrantedAuthority(perfilEnum.getDescricao()));
    }

        
}
