package com.profilewebsite.finalproject.service;

import com.profilewebsite.finalproject.model.Quiz;
import com.profilewebsite.finalproject.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository repo;
    public QuizService(QuizRepository repo){ this.repo = repo; }

    public List<Quiz> findAll(){ return repo.findAll(); }
    public Optional<Quiz> findById(Long id){ return repo.findById(id); }
    public Quiz getQuizById(Long id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    public Quiz save(Quiz q){ return repo.save(q); }
    public List<Quiz> findByClassRoomId(Long id){ return repo.findByClassRoomId(id); }
    public List<Quiz> findByTeacherId(Long id){ return repo.findByTeacherId(id); }
}
