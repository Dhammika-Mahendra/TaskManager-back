package com.example.demo.services.auth;

import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.UserDto;

public interface AuthService {
    UserDto signUpUser(SignUpRequest signUpRequest);

    boolean hasUserWithEmail(String email);
}

