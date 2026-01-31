package com.quotejournal.service;

import com.quotejournal.dto.*;
import com.quotejournal.entity.*;
import com.quotejournal.exception.ResourceNotFoundException;
import com.quotejournal.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    public UserService(UserRepository userRepository, PasswordEncoder encoder){
        this.userRepository=userRepository;
        this.encoder=encoder;
    }
    public User getUserByName(String username){
        return userRepository.findByName(username).orElseThrow(()->new ResourceNotFoundException("No user exists by this name"));
    }
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("No user exists with this email"));
    }
    @Transactional
    public UserResponse updateUser(UserRequest userRequest){
        String currName= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(currName).orElseThrow(()->new ResourceNotFoundException("No user found with this username"));
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPassword(encoder.encode(userRequest.password()));
        return new UserResponse(user.getName(), user.getEmail(), user.isVerified());
    }
    @Transactional
    public String deleteUser(String password){
        String currName=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(currName).orElseThrow(()->new ResourceNotFoundException("no user found with this username"));
        if(!encoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Invalid password. Can't delete user");
        }
        userRepository.delete(user);
        return "Deleted";
    }
}
