package com.quotejournal.controller;

import com.quotejournal.dto.UserRequest;
import com.quotejournal.service.AuthService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService=authService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest){
        String status=authService.register(userRequest);
        return ResponseEntity.ok(status);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserRequest loginRequest){
        String email= loginRequest.email();
        String password= loginRequest.password();
        String jwtToken= authService.login(email, password);
        Map<String, String> response=new HashMap<>();
        response.put("token", jwtToken);
        return ResponseEntity.ok(response);
    }
}
