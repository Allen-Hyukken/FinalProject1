package com.profilewebsite.finalproject.service;

import com.profilewebsite.finalproject.model.Quiz;
import java.util.List;
import java.util.Optional;

public interface QuizService {
    Quiz save(Quiz quiz);
    Optional<Quiz> findById(Long id);
    List<Quiz> findByTeacherId(Long teacherId);
    List<Quiz> findAll();
}

