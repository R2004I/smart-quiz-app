package com.genie.quiz.service;

import com.genie.quiz.entity.JavaScript_questions;
import com.genie.quiz.entity.QuizQuestion;
import com.genie.quiz.exception.ResourceNotFound;
import com.genie.quiz.repo.JavaScript_questions_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JavaScript_quiz_service {

    @Autowired
    private JavaScript_questions_Repo repo;

    public List<JavaScript_questions> getAllQuestions(){

        return repo.findAll();
    }

    public JavaScript_questions saveQuestion(JavaScript_questions question){
        return repo.save(question);
    }

    public String deleteQuestion(Long id){
        JavaScript_questions res = repo.findById(id).orElseThrow(()-> new ResourceNotFound("Question with id "+id+" not found"));
        repo.deleteById(id);
        return "Question with id "+id+" deleted successfully";
    }

    public JavaScript_questions updateQuestion(Map<String,Object> updates, Long id){
        JavaScript_questions optionalQuestion = repo.findById(id).orElseThrow(()->new ResourceNotFound("Question with id "+id+" not found"));

        // Apply updates dynamically
        updates.forEach((key, value) -> {
            switch (key) {
                case "question":
                    optionalQuestion.setJavaScript_Question((String) value);
                    break;
                case "options":
                    optionalQuestion.setJavaScript_options((List<String>) value);
                    break;
                case "correctAns":
                    optionalQuestion.setJavaScript_correct_option((String) value);
                    break;
            }
        });
        repo.save(optionalQuestion);
        return optionalQuestion;
    }
}
