package com.patriciasantos.desafio.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.patriciasantos.desafio.models.enums.ProfileEnum;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NonNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String username;

    @JsonProperty( access = Access.WRITE_ONLY)
    @Column(name = "password", length = 60, nullable = false)
    @NonNull
    @NotEmpty
    @Size(min = 2, max = 60)
    private String password;

    @Column(name = "status", nullable = false)
    @NonNull
    private Boolean status = true;

    @JsonProperty( access = Access.WRITE_ONLY)
    @Column(name = "profile", nullable = false) 
    private Integer profile;
    

    public User() {

    }

    public User(final Long id, final String username, final String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    } 

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }


    public Boolean isStatus() {
        return this.status;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(final Boolean status) {
        this.status = status;
    }

    public Integer getProfile() {
        return this.profile;
    }

    public void setProfile(final Integer profile) {
        this.profile = profile;
    }
   

    public ProfileEnum getProfileEnum() {
        return ProfileEnum.toEnum(this.profile);
    }

    public void setProfileEnum(final ProfileEnum profileEnum) {
        this.profile = profileEnum.getCode();
    }

    @OneToMany(mappedBy = "user")
    private List<Classroom> classrooms = new ArrayList<Classroom>();

    public static class UserBuilder {

        private User user;

        public UserBuilder create() {
            user = new User();
            return this;
        }

        public UserBuilder withUsername(final String username) {
            user.setUsername(username);
            return this;
        }

        public UserBuilder withPassword(final String password) {
            user.setPassword(password);
            return this;
        }

        public UserBuilder withProfile(final Integer profile) {
            user.setProfile(profile);
            return this;
        }

        public User build() {
            return user;
        }

    }
    
}

