package com.profilewebsite.finalproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.profilewebsite.finalproject.model.Attempt;
import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByQuizId(Long quizId);
    List<Attempt> findByStudentId(Long studentId);
}

