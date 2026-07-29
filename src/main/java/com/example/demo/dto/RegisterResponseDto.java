package com.example.demo.dto;

public class RegisterResponseDto {

    private String msg;

    public RegisterResponseDto(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
