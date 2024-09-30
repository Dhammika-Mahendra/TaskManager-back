package com.example.demo.dto;

import com.example.demo.enums.UserType;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private int id;
    private String name;
    private UserType role;
}
