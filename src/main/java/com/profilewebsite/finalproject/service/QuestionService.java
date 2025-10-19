package com.profilewebsite.finalproject.service;

import com.profilewebsite.finalproject.model.Question;
import com.profilewebsite.finalproject.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository repo;
    public QuestionService(QuestionRepository repo){ this.repo = repo; }

    public Question save(Question q){ return repo.save(q); }
    public Optional<Question> findById(Long id){ return repo.findById(id); }
    public List<Question> findByQuizId(Long quizId){ return repo.findQuestionsByQuizOrderByQIndexAsc(quizId); }
    public void deleteById(Long id){ repo.deleteById(id); }
}
