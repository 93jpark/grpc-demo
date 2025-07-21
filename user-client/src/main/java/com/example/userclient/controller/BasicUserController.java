package com.example.userclient.controller;

import com.example.userclient.service.UserClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BasicUserController {

    private final UserClientService userService;

    @PostMapping("/create")
    public void createUser() {
        userService.createUser("john_doe", "<EMAIL>");
    }

    @GetMapping("/get")
    public void readUser() {
        userService.getUserInfo(1);
    }

    @PatchMapping("/update")
    public void updateUser() {
        userService.updateUser(null,"john.l.doe", "john.l.doe@gamil.com");
    }

    @DeleteMapping("delete")
    public void deleteUser() {
        userService.deleteUser(1);
    }

}
