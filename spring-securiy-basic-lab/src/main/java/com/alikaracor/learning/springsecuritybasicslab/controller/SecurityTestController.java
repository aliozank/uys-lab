package com.alikaracor.learning.springsecuritybasicslab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Bu alan herkese açık";
    }

    @GetMapping("/user")
    public String userEndpoint() {
        return "USER alanına eriştin";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "ADMIN alanına eriştin";
    }
}