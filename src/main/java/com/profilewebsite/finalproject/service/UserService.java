package com.profilewebsite.finalproject.service;

import com.profilewebsite.finalproject.model.User;
import com.profilewebsite.finalproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List; // ADDED

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository; //
    }

    public User save(User u) {
        return userRepository.save(u); //
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email); //
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id); //
    }

    /**
     * ADDED: Required by TeacherController.viewClass
     * This method finds all students enrolled in a specific class.
     */
    public List<User> findStudentsByClassRoom(Long classRoomId) {
        // This assumes your UserRepository has a method to find users
        // by the ID of a class they are enrolled in.
        return userRepository.findByClasses_Id(classRoomId);
    }

    // -----------------------------
    // Get current logged-in user
    // -----------------------------
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //
        String email = auth.getName(); //
        return findByEmail(email).orElseThrow(() -> new RuntimeException("User not found")); //
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId(); //
    }
}