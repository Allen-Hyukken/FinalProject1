package com.profilewebsite.finalproject.repository;


import com.profilewebsite.finalproject.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByTeacherId(Long teacherId);
    List<Quiz> findByClassRoomId(Long classRoomId);
}


