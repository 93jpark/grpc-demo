package com.example.userclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

    @GetMapping("/")
    public String queryUser() {
        return "user";
    }

}
