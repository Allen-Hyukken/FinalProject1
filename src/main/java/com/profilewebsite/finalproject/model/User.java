package com.profilewebsite.finalproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List; // ADDED

@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String role; // TEACHER / STUDENT

    /**
     * ADDED: This is the inverse side of the relationship in ClassRoom.java
     * and is required for the UserRepository.findByClasses_Id method.
     */
    @ManyToMany(mappedBy = "students")
    private List<ClassRoom> classes;
}