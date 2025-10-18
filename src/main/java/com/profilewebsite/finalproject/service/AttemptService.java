package com.profilewebsite.finalproject.service;


import com.profilewebsite.finalproject.model.Attempt;
import java.util.Map;

public interface AttemptService {
    Attempt submitAnswers(Long quizId, Long studentId, Map<String,String> answers);
}
