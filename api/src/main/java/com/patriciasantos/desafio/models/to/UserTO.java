package com.patriciasantos.desafio.models.to;

public class UserTO {

    private String username;
    private String password;
    private Integer profile;


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

    public Integer getProfile() {
        return profile;
    }    
}