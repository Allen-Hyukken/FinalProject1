package com.profilewebsite.finalproject.repository;

import com.profilewebsite.finalproject.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByQuizId(Long quizId);
}
