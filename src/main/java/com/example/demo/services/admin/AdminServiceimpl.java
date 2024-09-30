package com.example.demo.services.admin;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceimpl implements AdminService {

    private final UserRepo userRepo;
    @Override
    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream().filter(user -> user.getRole().name().equals("EMPLOYEE"))
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }
}
