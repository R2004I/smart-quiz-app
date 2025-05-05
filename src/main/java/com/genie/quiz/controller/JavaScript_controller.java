package com.genie.quiz.controller;


import com.genie.quiz.entity.JavaScript_questions;
import com.genie.quiz.entity.QuizQuestion;
import com.genie.quiz.service.JavaScript_quiz_service;
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
@Tag(name = " JavaScript quiz Service Controller", description = "APIs for performing quiz operation")
public class JavaScript_controller {

    @Autowired
    private JavaScript_quiz_service service;

    @Operation(summary = "See all quiz questions", description = "Get all the quiz question on JavaScript",security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "Question fetched successfully",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/java-script-questions")
    public List<JavaScript_questions> getAllQuestions(){
        return service.getAllQuestions();
    }


    @Operation(summary = "Save new quiz question on JavaScript",description = "Only Admin API",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "Question saved successfully",
            content = @Content(mediaType = "application/json"))
    @PostMapping("/save/java-script-questions")
    public JavaScript_questions saveQuestion(@RequestBody JavaScript_questions question) {
        return service.saveQuestion(question);
    }


    @Operation(summary = "Delete quiz question on JavaScript", security = @SecurityRequirement(name = "basicAuth"), description = "Only Admin API")
    @ApiResponse(responseCode = "200", description = "Question deleted successfully",
            content = @Content(mediaType = "application/json"))
    @DeleteMapping("admin/js/delete-qs")
    public ResponseEntity<?> deleteQuestion(Long id){
        String res = service.deleteQuestion(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @Operation(summary = "Update quiz question on JavaScript", security = @SecurityRequirement(name = "basicAuth"), description = "Only Admin API")
    @ApiResponse(responseCode = "200", description = "Question updated successfully",
            content = @Content(mediaType = "application/json"))
    @PatchMapping("/admin/js/update-quiz")
    public ResponseEntity<?> updateQuestions(
            @RequestParam Long id,
            @RequestBody Map<String, Object> updates
    ) {
        JavaScript_questions response = service.updateQuestion(updates,id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
