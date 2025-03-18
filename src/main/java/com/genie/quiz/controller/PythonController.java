package com.genie.quiz.controller;


import com.genie.quiz.entity.PythonQuestions;
import com.genie.quiz.service.Python_quiz_service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Python quiz Service Controller", description = "APIs for performing quiz operation")
public class PythonController {

    @Autowired
    private Python_quiz_service service;


    @Operation(summary = "See all quiz questions", description = "Get all the quiz question on Python",security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "Question fetched successfully",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/python-quiz-questions")
    public List<PythonQuestions> getAllQuestions(){
        return service.getAllQuestions();
    }


    @Operation(summary = "Save new quiz question on Python",description = "Only Admin API",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "Question saved successfully",
            content = @Content(mediaType = "application/json"))
    @PostMapping("/save/python-quiz-question")
    public PythonQuestions saveQuestion(@RequestBody PythonQuestions question){
        return service.saveQuestion(question);
    }


    @Operation(summary = "Delete quiz question on Python", security = @SecurityRequirement(name = "basicAuth"), description = "Only Admin API")
    @ApiResponse(responseCode = "200", description = "Question deleted successfully",
            content = @Content(mediaType = "application/json"))
    @DeleteMapping("admin/py/delete-qs")
    public ResponseEntity<?> deleteQuestion(Long id){
        String res = service.deleteQuestion(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @Operation(summary = "Update quiz question on Python", security = @SecurityRequirement(name = "basicAuth"), description = "Only Admin API")
    @ApiResponse(responseCode = "200", description = "Question updated successfully",
            content = @Content(mediaType = "application/json"))
    @PatchMapping("/admin/py/update-quiz")
    public ResponseEntity<?> updateQuestions(
            @RequestParam Long id,
            @RequestBody Map<String, Object> updates
    ) {
        PythonQuestions response = service.updateQuestion(updates,id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
