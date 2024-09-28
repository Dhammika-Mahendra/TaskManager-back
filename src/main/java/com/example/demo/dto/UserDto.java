package com.example.demo.dto;

import com.example.demo.enums.UserType;
import lombok.Data;

@Data
public class UserDto {
    public int id;
    public String name;
    public String email;
    public String password;
    public UserType role;
}
