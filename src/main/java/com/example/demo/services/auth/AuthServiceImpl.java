package com.example.demo.services.auth;

import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.enums.UserType;
import com.example.demo.repos.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;

    @PostConstruct
    public void createAdminAccount(){
        Optional<User> admin = userRepo.findByRole(UserType.ADMIN);
        if(admin.isEmpty()) {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setPassword("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin123"));
            user.setRole(UserType.ADMIN);
            userRepo.save(user);
            System.out.println("Admin account created successfully");
        }else{
            System.out.println("Admin account already exists");
        }
    }

    @Override
    public UserDto signUpUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        user.setRole(UserType.EMPLOYEE);
        User createdUser = userRepo.save(user);
        return createdUser.getUserDto();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepo.findByEmail(email).isPresent();
    }
}
