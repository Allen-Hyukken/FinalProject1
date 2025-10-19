package com.profilewebsite.finalproject.service;

import com.profilewebsite.finalproject.model.Answer;
import com.profilewebsite.finalproject.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> findByAttemptId(Long attemptId) {
        return answerRepository.findByAttemptId(attemptId);
    }
}
