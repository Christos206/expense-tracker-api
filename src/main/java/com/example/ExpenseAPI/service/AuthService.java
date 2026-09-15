package com.example.ExpenseAPI.service;

import com.example.ExpenseAPI.dto.LoginRequestDto;
import com.example.ExpenseAPI.dto.LoginResponseDto;
import com.example.ExpenseAPI.dto.RegisterRequestDto;
import com.example.ExpenseAPI.dto.RegisterResponseDto;
import com.example.ExpenseAPI.model.Role;
import com.example.ExpenseAPI.model.RoleEnum;
import com.example.ExpenseAPI.model.User;
import com.example.ExpenseAPI.repository.RoleRepository;
import com.example.ExpenseAPI.repository.UserRepository;
import com.example.ExpenseAPI.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Role optionalRole = roleRepository.findByName(RoleEnum.USER).orElseThrow(() -> new RuntimeException("USER role not found"));

        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRole(optionalRole);

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

    public void deleteuser(Long id){
        userRepository.deleteById(id);
    }

}
