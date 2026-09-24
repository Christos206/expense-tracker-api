package com.example.ExpenseAPI.controller;

import com.example.ExpenseAPI.dto.*;
import com.example.ExpenseAPI.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public RegisterResponseDto register(@Valid @RequestBody RegisterRequestDto requestDto) {

        return authService.register(requestDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Returns a JWT token after successful authentication.")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto requestDto) {

        return authService.login(requestDto);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary =  "Delete a user")
    public void deleteUser(@PathVariable Long id) {

        authService.deleteUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Retrieve all users", description = "Retrives All users")
    public List<UserInfResponseDto> getallusers() {
        return authService.getAllusers();
    }

}
