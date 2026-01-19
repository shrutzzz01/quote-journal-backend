package com.quotejournal.controller;

import com.quotejournal.dto.UserRequest;
import com.quotejournal.dto.UserResponse;
import com.quotejournal.entity.User;
import com.quotejournal.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PutMapping("/update")
    public UserResponse updateUser(@RequestBody UserRequest userRequest){
        return userService.updateUser(userRequest);
    }
    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam String password){
        return userService.deleteUser(password);
    }
}
