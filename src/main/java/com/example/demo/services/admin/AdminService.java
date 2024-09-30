package com.example.demo.services.admin;

import com.example.demo.dto.UserDto;

import java.util.List;

public interface AdminService {
    public List<UserDto> getAllUsers();
}
