package com.profilewebsite.finalproject.repository;

import com.profilewebsite.finalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List; // ADDED
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    /**
     * ADDED: Required by UserService.findStudentsByClassRoom
     * Finds all users (students) who are enrolled in a class with the given ID.
     * "Classes" must match the field name in your User model.
     */
    List<User> findByClasses_Id(Long classRoomId);
}