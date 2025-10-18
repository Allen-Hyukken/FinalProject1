package com.profilewebsite.finalproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.profilewebsite.finalproject.model.Choice;
import java.util.List;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    List<Choice> findByQuestionId(Long questionId);
}

