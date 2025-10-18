package com.profilewebsite.finalproject.service.impl;


import org.springframework.stereotype.Service;
import com.profilewebsite.finalproject.service.QuizService;
import com.profilewebsite.finalproject.repository.QuizRepository;
import com.profilewebsite.finalproject.model.Quiz;
import java.util.*;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;

    public QuizServiceImpl(QuizRepository quizRepository){
        this.quizRepository = quizRepository;
    }

    @Override
    public Quiz save(Quiz quiz){ return quizRepository.save(quiz); }

    @Override
    public Optional<Quiz> findById(Long id){ return quizRepository.findById(id); }

    @Override
    public List<Quiz> findByTeacherId(Long teacherId){ return quizRepository.findByTeacherId(teacherId); }

    @Override
    public List<Quiz> findAll(){ return quizRepository.findAll(); }
}

