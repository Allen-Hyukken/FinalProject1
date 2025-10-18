package com.profilewebsite.finalproject.model;


import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ClassRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String code;

    @ManyToOne
    private User teacher;

    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();
}


