package com.example.ExpenseAPI.config;

import com.example.ExpenseAPI.model.Role;
import com.example.ExpenseAPI.model.RoleEnum;
import com.example.ExpenseAPI.repository.RoleRepository;
import com.example.ExpenseAPI.repository.UserRepository;
import com.example.ExpenseAPI.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {return args -> {

        if (roleRepository.findByName(RoleEnum.USER).isEmpty()) {
            Role userRole = new Role();
            userRole.setName(RoleEnum.USER);
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName(RoleEnum.ADMIN).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName(RoleEnum.ADMIN);
            roleRepository.save(adminRole);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {

            User admin = new User();

            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(roleRepository.findByName(RoleEnum.ADMIN).orElseThrow(() -> new RuntimeException("USER role not found")));

            userRepository.save(admin);
        }
    };
    }

}
