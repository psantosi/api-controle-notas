package com.patriciasantos.desafio.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.patriciasantos.desafio.models.User;
import com.patriciasantos.desafio.models.enums.ProfileEnum;
import com.patriciasantos.desafio.models.to.UserTO;
import com.patriciasantos.desafio.repositories.UserRepository;
import com.patriciasantos.desafio.security.UserSpringSecurity;
import com.patriciasantos.desafio.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class UserService {
   
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(final UserRepository userRepository,
                          final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User find(final Long id) {
        final User user = this.userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
        this.validateIfUserIsActive(user);
        return user;
    }

    public User getAuthenticatedUser() {
        final UserSpringSecurity userSpringSecurity = this.authenticatedUser();
        return this.find(userSpringSecurity.getId());
    }

    public Long getAuthenticatedUserId() {
        final UserSpringSecurity userSpringSecurity = this.authenticatedUser();
        return userSpringSecurity.getId();
    }


    @Transactional(rollbackOn = Exception.class)
    public User create(final UserTO userTO) {        
        final User user = new User.UserBuilder().create()
        .withUsername(userTO.getUsername())
        .withPassword(this.bCryptPasswordEncoder.encode(userTO.getPassword()))
        .withProfile(ProfileEnum.USER.getCode())
        .build();
        
        return this.userRepository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void update(final Long id, final UserTO userTO) {
        final User user = this.find(id);
        user.setUsername(userTO.getUsername());
        user.setPassword(this.bCryptPasswordEncoder.encode(userTO.getPassword()));
        this.userRepository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void delete(final Long id) {
        final User user = this.find(id);
        user.setStatus(false);
        this.userRepository.save(user);
    }

    public void validateIfUserIsActive(final User user) {
        if (!user.isStatus()) {
            throw new ObjectNotFoundException("Usuário excluido.");
        }
    }

    public UserSpringSecurity authenticatedUser() {
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
    
}