package com.example.demo.controller.auth;

import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser (@RequestBody SignUpRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE). body ("User already exist with this email");
        UserDto createdUserDto = authService.signUpUser(signupRequest);
        if (createdUserDto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST). body ("User not created");
        return ResponseEntity.status(HttpStatus.CREATED). body (createdUserDto);
    }

}
