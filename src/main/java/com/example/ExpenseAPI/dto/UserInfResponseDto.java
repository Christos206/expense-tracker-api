package com.example.ExpenseAPI.dto;

import com.example.ExpenseAPI.model.Role;
import com.example.ExpenseAPI.model.RoleEnum;

public class UserInfResponseDto {

    private Long id;

    private String username;

    private RoleEnum role;

    public UserInfResponseDto(Long id, String username, RoleEnum role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
