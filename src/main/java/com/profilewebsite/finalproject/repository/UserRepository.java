package com.profilewebsite.finalproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.profilewebsite.finalproject.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}


