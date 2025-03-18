package com.genie.quiz.service;

import com.genie.quiz.entity.NetQuiz_Questions;
import com.genie.quiz.entity.QuizQuestion;
import com.genie.quiz.exception.ResourceNotFound;
import com.genie.quiz.repo.Net_Quiz_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Net_Quiz_Service {

    @Autowired
    private Net_Quiz_Repo repo;

    public List<NetQuiz_Questions> getAllQuestions()
    {
        return repo.findAll();
    }

    public NetQuiz_Questions saveQuestion(NetQuiz_Questions question){
        return repo.save(question);
    }

    public String deleteQuestion(Long id){
        NetQuiz_Questions res = repo.findById(id).orElseThrow(()-> new ResourceNotFound("Question with id "+id+" not found"));
        repo.deleteById(id);
        return "Question with id "+id+" deleted successfully";
    }

    public NetQuiz_Questions updateQuestion(Map<String,Object> updates, Long id){
        NetQuiz_Questions optionalQuestion = repo.findById(id).orElseThrow(()->new ResourceNotFound("Question with id "+id+" not found"));

        // Apply updates dynamically
        updates.forEach((key, value) -> {
            switch (key) {
                case "question":
                    optionalQuestion.setQuizQuestionText((String) value);
                    break;
                case "options":
                    optionalQuestion.setQuizOptions((List<String>) value);
                    break;
                case "correctAns":
                    optionalQuestion.setQuizAnswer((String) value);
                    break;
            }
        });

        repo.save(optionalQuestion);
        return optionalQuestion;
    }
}
