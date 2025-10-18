package com.profilewebsite.finalproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.profilewebsite.finalproject.model.Question;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuizId(Long quizId);
}

