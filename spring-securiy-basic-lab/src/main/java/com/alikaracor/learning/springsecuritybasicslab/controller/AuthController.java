package com.alikaracor.learning.springsecuritybasicslab.controller;

import com.alikaracor.learning.springsecuritybasicslab.dto.LoginRequest;
import com.alikaracor.learning.springsecuritybasicslab.dto.LoginResponse;
import com.alikaracor.learning.springsecuritybasicslab.dto.RegisterRequest;
import com.alikaracor.learning.springsecuritybasicslab.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest loginRequest
    ) {
        return authService.login(loginRequest);
    }
    @PostMapping("/register")
    public String register(
            @Valid @RequestBody RegisterRequest registerRequest
    ) {
        return authService.register(registerRequest);
    }

}