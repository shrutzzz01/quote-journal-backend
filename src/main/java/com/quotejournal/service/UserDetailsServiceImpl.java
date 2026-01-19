package com.quotejournal.service;

import com.quotejournal.entity.User;
import com.quotejournal.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    // Inside com.quotejournal.service.UserDetailsServiceImpl

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("--- DEBUG UserDetailsService ---");
        System.out.println("Attempting to find user in DB with email: " + email); // <-- NEW DEBUG LINE

        User appUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        System.out.println("SUCCESS: User found in DB. Name: " + appUser.getName()); // <-- NEW DEBUG LINE
        System.out.println("--- END DEBUG UserDetailsService ---");

        // ... rest of the method (returning the UserDetails object)

        return new org.springframework.security.core.userdetails.User(
                appUser.getEmail(),
                appUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(appUser.getRole().name()))
        );
    }
}
