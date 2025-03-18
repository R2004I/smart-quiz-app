package com.genie.quiz.service;

import com.genie.quiz.entity.QuizQuestion;
import com.genie.quiz.exception.ResourceNotFound;
import com.genie.quiz.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    public List<QuizQuestion> getAllQuestions()
    {
        return questionRepo.findAll();
    }

    public QuizQuestion saveQuestion(QuizQuestion question){
        return questionRepo.save(question);
    }

    public String deleteQuestion(Long id){
        QuizQuestion res = questionRepo.findById(id).orElseThrow(()-> new ResourceNotFound("Question with id "+id+" not found"));
        questionRepo.deleteById(id);
        return "Question with id "+id+" deleted successfully";
    }

    public QuizQuestion updateQuestion(Map<String,Object> updates, Long id){
        QuizQuestion optionalQuestion = questionRepo.findById(id).orElseThrow(()->new ResourceNotFound("Question with id "+id+" not found"));

        // Apply updates dynamically
        updates.forEach((key, value) -> {
            switch (key) {
                case "question":
                    optionalQuestion.setQuestionText((String) value);
                    break;
                case "options":
                    optionalQuestion.setOptions((List<String>) value);
                    break;
                case "correctAns":
                    optionalQuestion.setCorrectAnswer((String) value);
                    break;
            }
        });

        questionRepo.save(optionalQuestion);
        return optionalQuestion;
    }
}
