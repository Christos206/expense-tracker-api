package com.example.ExpenseAPI.repository;

import com.example.ExpenseAPI.model.Role;
import com.example.ExpenseAPI.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);

}
