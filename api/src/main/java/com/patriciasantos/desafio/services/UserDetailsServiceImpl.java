package com.patriciasantos.desafio.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.patriciasantos.desafio.models.User;
import com.patriciasantos.desafio.repositories.UserRepository;
import com.patriciasantos.desafio.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    public UserDetailsServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = Optional.ofNullable(this.userRepository.findByUsername(username)).orElseThrow(() -> new UsernameNotFoundException(" Usuário não encontrado"));
        return new UserSpringSecurity(user.getId(), user.getUsername(), user.getPassword(), user.getProfileEnum());    
    }
    
}
