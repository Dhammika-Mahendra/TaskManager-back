package com.example.demo.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String name;
    private String password;
    private String email;
}
