package com.example.ExpenseAPI.service;

import com.example.ExpenseAPI.dto.*;
import com.example.ExpenseAPI.exception.InvalidCredentialsException;
import com.example.ExpenseAPI.exception.ResourceNotFoundException;
import com.example.ExpenseAPI.model.Role;
import com.example.ExpenseAPI.model.RoleEnum;
import com.example.ExpenseAPI.model.User;
import com.example.ExpenseAPI.repository.RoleRepository;
import com.example.ExpenseAPI.repository.UserRepository;
import com.example.ExpenseAPI.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    public RegisterResponseDto register(RegisterRequestDto requestDto) {
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        Role defaultRole = roleRepository.findByName(RoleEnum.USER).orElseThrow(() -> new RuntimeException("USER role not found"));

        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRole(defaultRole);

        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        userRepository.save(user);

        return new RegisterResponseDto("User registered successfully");
    }

    public LoginResponseDto login(LoginRequestDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());

        return new LoginResponseDto(token);
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }

    public List<UserInfResponseDto> getAllusers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserInfResponseDto(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().getName()
                ))
                .toList();
    }

}
