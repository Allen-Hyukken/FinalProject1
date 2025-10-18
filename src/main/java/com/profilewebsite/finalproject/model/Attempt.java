package com.profilewebsite.finalproject.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Attempt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private User student;

    private Double score;

    private LocalDateTime submittedAt;
}


