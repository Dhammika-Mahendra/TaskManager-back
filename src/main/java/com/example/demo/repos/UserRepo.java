package com.example.demo.repos;

import com.example.demo.entity.User;
import com.example.demo.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String username);
    Optional<User> findByRole(UserType type);
}
