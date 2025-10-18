package com.profilewebsite.finalproject.service.impl;


import org.springframework.stereotype.Service;
import com.profilewebsite.finalproject.service.AttemptService;
import com.profilewebsite.finalproject.repository.*;
import com.profilewebsite.finalproject.model.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AttemptServiceImpl implements AttemptService {
    private final QuizRepository quizRepo;
    private final UserRepository userRepo;
    private final AttemptRepository attemptRepo;
    private final QuestionRepository questionRepo;
    private final ChoiceRepository choiceRepo;
    private final AnswerRepository answerRepo;

    public AttemptServiceImpl(QuizRepository quizRepo, UserRepository userRepo,
                              AttemptRepository attemptRepo, QuestionRepository questionRepo,
                              ChoiceRepository choiceRepo, AnswerRepository answerRepo){
        this.quizRepo = quizRepo; this.userRepo = userRepo; this.attemptRepo = attemptRepo;
        this.questionRepo = questionRepo; this.choiceRepo = choiceRepo; this.answerRepo = answerRepo;
    }

    @Override
    public Attempt submitAnswers(Long quizId, Long studentId, Map<String,String> answers){
        Quiz quiz = quizRepo.findById(quizId).orElseThrow();
        User student = userRepo.findById(studentId).orElseThrow();
        Attempt attempt = new Attempt();
        attempt.setQuiz(quiz);
        attempt.setStudent(student);
        attempt.setSubmittedAt(LocalDateTime.now());
        attempt = attemptRepo.save(attempt);

        List<Question> questions = questionRepo.findByQuizId(quizId);
        int total = 0; int correctCount = 0;
        for(Question q : questions){
            total++;
            String key = "q_" + q.getId();
            String value = answers.get(key);
            Answer a = new Answer();
            a.setAttempt(attempt);
            a.setQuestion(q);
            if("MCQ".equalsIgnoreCase(q.getType())){
                if(value != null && !value.isEmpty()){
                    long choiceId = Long.parseLong(value);
                    Choice c = choiceRepo.findById(choiceId).orElse(null);
                    a.setSelectedChoice(c);
                    boolean ok = c != null && c.isCorrect();
                    a.setCorrect(ok);
                    if(ok) correctCount++;
                } else {
                    a.setCorrect(false);
                }
            } else if("TF".equalsIgnoreCase(q.getType())){
                boolean ok = q.getExpectedAnswer() != null && q.getExpectedAnswer().equalsIgnoreCase(value);
                a.setGivenText(value);
                a.setCorrect(ok);
                if(ok) correctCount++;
            } else if("IDENT".equalsIgnoreCase(q.getType())){
                boolean ok = q.getExpectedAnswer() != null && q.getExpectedAnswer().equalsIgnoreCase(value);
                a.setGivenText(value);
                a.setCorrect(ok);
                if(ok) correctCount++;
            } else {
                // ESSAY -> manual grading
                a.setGivenText(value);
                a.setCorrect(false);
            }
            answerRepo.save(a);
        }

        double score = total == 0 ? 0.0 : (100.0 * correctCount / (double) total);
        attempt.setScore(score);
        attemptRepo.save(attempt);
        return attempt;
    }
}

