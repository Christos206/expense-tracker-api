package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.dto.RegisterRequestDto;
import com.example.demo.dto.RegisterResponseDto;
import com.example.demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public RegisterResponseDto register(@RequestBody RegisterRequestDto requestDto) {

        return authService.register(requestDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Returns a JWT token after successful authentication.")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {

        return authService.login(requestDto);
    }
}
