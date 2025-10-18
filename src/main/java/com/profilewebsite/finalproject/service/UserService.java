package com.profilewebsite.finalproject.service;


import com.profilewebsite.finalproject.model.User;
import java.util.Optional;

public interface UserService {
    User register(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}

