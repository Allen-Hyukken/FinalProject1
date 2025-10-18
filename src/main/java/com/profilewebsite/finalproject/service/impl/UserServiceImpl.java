package com.profilewebsite.finalproject.service.impl;

import org.springframework.stereotype.Service;
import com.profilewebsite.finalproject.service.UserService;
import com.profilewebsite.finalproject.repository.UserRepository;
import com.profilewebsite.finalproject.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user){
        userRepository.save(user);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id){ return userRepository.findById(id); }
}
