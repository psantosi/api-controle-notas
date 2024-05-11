package com.patriciasantos.desafio.models.to;

public class UsuarioTO {

    private String username;
    private String password;
    private Integer perfil;


    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Integer getPerfil() {
        return perfil;
    }

    public void setPerfil(final Integer perfil) {
        this.perfil = perfil;
    }

    
}
