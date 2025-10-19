package com.profilewebsite.finalproject.service;

import com.profilewebsite.finalproject.model.*;
import com.profilewebsite.finalproject.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AttemptService {
    private final AttemptRepository attemptRepo;
    private final AnswerRepository answerRepo;
    private final QuizService quizService;
    private final QuestionService questionService;
    private final ChoiceRepository choiceRepository;
    private final UserService userService;

    public AttemptService(AttemptRepository attemptRepo, AnswerRepository answerRepo,
                          QuizService quizService, QuestionService questionService,
                          ChoiceRepository choiceRepository, UserService userService) {
        this.attemptRepo = attemptRepo;
        this.answerRepo = answerRepo;
        this.quizService = quizService;
        this.questionService = questionService;
        this.choiceRepository = choiceRepository;
        this.userService = userService;
    }

    public List<Attempt> findByQuiz(Long quizId) {
        return attemptRepo.findByQuizId(quizId);
    }

    @Transactional
    public Attempt submitAnswers(Long quizId, Long studentId, Map<String,String> params) {
        Quiz quiz = quizService.findById(quizId).orElseThrow();
        User student = userService.findById(studentId).orElseThrow();

        Attempt attempt = new Attempt();
        attempt.setQuiz(quiz);
        attempt.setStudent(student);
        attempt.setSubmittedAt(java.time.LocalDateTime.now());

        List<Answer> answerList = new ArrayList<>();
        double score = 0.0;
        double maxAuto = 0.0; // number of auto-gradable questions

        // fetch questions
        List<Question> questions = quiz.getQuestions();
        if (questions == null) questions = questionService.findByQuizId(quizId);

        for (Question q : questions) {
            String key = "q_" + q.getId();
            if (!params.containsKey(key)) {
                // no answer submitted for that question
                continue;
            }
            String value = params.get(key); // for MCQ -> choice id; for others -> text/TRUE/FALSE

            Answer a = new Answer();
            a.setQuestion(q);
            a.setAttempt(attempt);

            boolean correct = false;

            switch (q.getType()) {
                case "MCQ":
                    // value should be choice id
                    try {
                        Long choiceId = Long.parseLong(value);
                        Optional<Choice> chOpt = choiceRepository.findById(choiceId);
                        if (chOpt.isPresent()) {
                            Choice ch = chOpt.get();
                            a.setChoice(ch);
                            a.setGivenText(ch.getText());
                            correct = ch.isCorrect();
                        }
                    } catch (NumberFormatException ignored) {}
                    maxAuto += 1;
                    if (correct) score += 1;
                    break;

                case "TF":
                    // value 'TRUE' or 'FALSE', q.correctAnswer expected 'TRUE'/'FALSE'
                    a.setGivenText(value);
                    if (q.getCorrectAnswer() != null) {
                        maxAuto += 1;
                        if (q.getCorrectAnswer().equalsIgnoreCase(value)) {
                            correct = true; score += 1;
                        }
                    }
                    break;

                case "IDENT":
                    a.setGivenText(value);
                    if (q.getCorrectAnswer() != null) {
                        maxAuto += 1;
                        if (q.getCorrectAnswer().trim().equalsIgnoreCase(value.trim())) {
                            correct = true; score += 1;
                        }
                    }
                    break;

                case "ESSAY":
                    a.setGivenText(value);
                    // not auto-graded; teacher will grade later
                    break;

                default:
                    a.setGivenText(value);
            }

            a.setCorrect(correct);
            answerList.add(a);
        }

        // if there were auto-gradable questions, compute normalized score relative to quiz.totalPoints if set
        double finalScore;
        if (maxAuto > 0) {
            double raw = score; // number correct
            // scale to quiz.totalPoints if > 0, else raw points
            double totalPoints = quiz.getTotalPoints() != null && quiz.getTotalPoints() > 0 ? quiz.getTotalPoints() : maxAuto;
            finalScore = (raw / maxAuto) * totalPoints;
        } else {
            finalScore = 0.0;
        }

        attempt.setScore(finalScore);
        attempt.setAnswers(answerList);

        Attempt saved = attemptRepo.save(attempt);
        // answers saved via cascade (persist), but ensure answers know attempt
        for (Answer aa : answerList) {
            aa.setAttempt(saved);
            answerRepo.save(aa);
        }
        return saved;
    }
}
