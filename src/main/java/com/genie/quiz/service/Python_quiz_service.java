package com.genie.quiz.service;

import com.genie.quiz.entity.PythonQuestions;
import com.genie.quiz.entity.QuizQuestion;
import com.genie.quiz.exception.ResourceNotFound;
import com.genie.quiz.repo.Python_question_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Python_quiz_service {

    @Autowired
    private Python_question_repo repo;

    public List<PythonQuestions> getAllQuestions(){
        return repo.findAll();
    }

    public PythonQuestions saveQuestion(PythonQuestions questions){
        return repo.save(questions);
    }

    public String deleteQuestion(Long id){
        PythonQuestions res = repo.findById(id).orElseThrow(()-> new ResourceNotFound("Question with id "+id+" not found"));
        repo.deleteById(id);
        return "Question with id "+id+" deleted successfully";
    }

    public PythonQuestions updateQuestion(Map<String,Object> updates, Long id){
        PythonQuestions optionalQuestion = repo.findById(id).orElseThrow(()->new ResourceNotFound("Question with id "+id+" not found"));

        // Apply updates dynamically
        updates.forEach((key, value) -> {
            switch (key) {
                case "question":
                    optionalQuestion.setPython_questions((String) value);
                    break;
                case "options":
                    optionalQuestion.setPy_options((List<String>) value);
                    break;
                case "correctAns":
                    optionalQuestion.setPy_correct_ans((String) value);
                    break;
            }
        });

        repo.save(optionalQuestion);
        return optionalQuestion;
    }
}
