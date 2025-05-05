package com.genie.quiz.controller;

import com.genie.quiz.entity.QuizQuestion;
import com.genie.quiz.service.QuestionService;
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
@Tag(name = " Java quiz Service Controller", description = "APIs for performing quiz operation")
public class JavaController {


    @Autowired
    private QuestionService questionService;

    @Operation(summary = "See all quiz questions",description = "Get all the quiz question on Java",security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "Question fetched successfully",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/questions")
    public List<QuizQuestion> getQuestions() {
        return questionService.getAllQuestions();
    }


    @Operation(summary = "Save new quiz question on Java",description = "Only Admin API",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "Question saved successfully",
            content = @Content(mediaType = "application/json"))
    @PostMapping("/save/java-questions")
    public QuizQuestion saveQuestion(@RequestBody QuizQuestion question) {
        return questionService.saveQuestion(question);
    }

    @Operation(summary = "Delete quiz question on Java", security = @SecurityRequirement(name = "basicAuth"), description = "Only Admin API")
    @ApiResponse(responseCode = "200", description = "Question deleted successfully",
            content = @Content(mediaType = "application/json"))
    @DeleteMapping("admin/java/delete-qs")
    public ResponseEntity<?> deleteQuestion(@RequestParam("id") Long id){
        String res = questionService.deleteQuestion(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @Operation(summary = "Update quiz question on Java", security = @SecurityRequirement(name = "basicAuth"), description = "Only Admin API")
    @ApiResponse(responseCode = "200", description = "Question updated successfully",
            content = @Content(mediaType = "application/json"))
    @PatchMapping("/admin/java/update-quiz")
    public ResponseEntity<?> updateQuestions(
            @RequestParam Long id,
            @RequestBody Map<String, Object> updates
    ) {
        QuizQuestion response = questionService.updateQuestion(updates,id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
