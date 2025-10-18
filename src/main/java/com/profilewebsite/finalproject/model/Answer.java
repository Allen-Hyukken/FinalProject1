package com.profilewebsite.finalproject.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Attempt attempt;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Choice selectedChoice;

    @Column(columnDefinition = "TEXT")
    private String givenText;

    private boolean correct;
}

