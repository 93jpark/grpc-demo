package com.example.userapiserver.controller;

import com.example.userapiserver.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping
    public User getUser() {
        User user = new User(1, "john_doe", "john.doe@example.com");
        return user;
    }

}
