package com.profilewebsite.finalproject.repository;

import com.profilewebsite.finalproject.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List; // ADDED

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    /**
     * ADDED: Required by AnswerService.findByAttemptId
     * Finds all answers associated with a single attempt.
     */
    List<Answer> findByAttemptId(Long attemptId);
}