package com.quotejournal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password"; // Change this to your desired password
        String hashedPassword = encoder.encode(rawPassword);
        System.out.println(hashedPassword);
    }
}