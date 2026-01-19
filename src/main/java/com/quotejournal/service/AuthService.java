package com.quotejournal.service;

import com.quotejournal.dto.UserRequest;
import com.quotejournal.entity.Role;
import com.quotejournal.entity.User;
import com.quotejournal.exception.ResourceNotFoundException;
import com.quotejournal.repository.UserRepository;
import com.quotejournal.security.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager){
        this.userRepository=userRepository;
        this.encoder=encoder;
        this.jwtUtil=jwtUtil;
        this.authenticationManager=authenticationManager;
    }
    public String register(UserRequest userRequest){
        if(userRepository.findByName(userRequest.name()).isPresent()){
            throw new RuntimeException("Name already taken");
        }
        if(userRepository.findByEmail(userRequest.email()).isPresent()){
            throw new RuntimeException("Email already registered");
        }
        User user=User.builder().name(userRequest.name()).email(userRequest.email()).password(encoder.encode(userRequest.password())).isVerified(true).role(Role.USER).build();
        userRepository.save(user);
        return "User created. Please login.";
    }
    // Inside AuthService.java

    public String login(String email, String password){
        System.out.println("--- DEBUG AUTH ---");
        System.out.println("Attempting login for email: " + email);
        // Removed the misleading and wasteful encoder.encode(password) line
        System.out.println("--- END DEBUG ---");

        try{
            // This is the line that performs the password verification against the DB hash
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        } catch(Exception e){
            // Log the actual exception type for deeper insight
            System.err.println("Authentication failed. Exception type: " + e.getClass().getName());
            throw new BadCredentialsException("Invalid email or password");
        }

        // Efficiently retrieve the user after successful authentication
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User not found after successful authentication"));

        return jwtUtil.generateToken(user);
    }
}
