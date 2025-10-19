package com.profilewebsite.finalproject.repository;

import com.profilewebsite.finalproject.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.quiz.id = :quizId ORDER BY q.qIndex DESC")
    List<Question> findQuestionsByQuizOrderByQIndexAsc(@Param("quizId") Long quizId);

}

