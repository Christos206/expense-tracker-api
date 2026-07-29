package com.example.ExpenseAPI.service;

import com.example.ExpenseAPI.dto.LoginRequestDto;
import com.example.ExpenseAPI.dto.LoginResponseDto;
import com.example.ExpenseAPI.dto.RegisterRequestDto;
import com.example.ExpenseAPI.dto.RegisterResponseDto;
import com.example.ExpenseAPI.model.User;
import com.example.ExpenseAPI.repository.UserRepository;
import com.example.ExpenseAPI.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public RegisterResponseDto register(RegisterRequestDto requestDto) {

        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        userRepository.save(user);

        return new RegisterResponseDto("User added Succesfully");
    }

    public LoginResponseDto login(LoginRequestDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());

        return new LoginResponseDto(token);
    }



}
