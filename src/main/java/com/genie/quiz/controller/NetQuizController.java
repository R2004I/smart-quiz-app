package com.genie.quiz.controller;

import com.genie.quiz.entity.NetQuiz_Questions;
import com.genie.quiz.entity.QuizQuestion;
import com.genie.quiz.service.Net_Quiz_Service;
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
@Tag(name = ".NET quiz Service Controller", description = "APIs for performing quiz operation")
public class NetQuizController {

    @Autowired
    private Net_Quiz_Service service;


    @Operation(summary = "See all quiz questions", description = "Get all the quiz question on .NET",security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "Question fetched successfully",
            content = @Content(mediaType = "application/json"))
    @GetMapping("/net-quiz/questions")
    public ResponseEntity<?> getQuestions() {
        List<NetQuiz_Questions> questions = service.getAllQuestions();
        return ResponseEntity.status(HttpStatus.OK).body(questions);
    }


    @Operation(summary = "Save new quiz question on .NET",description = "Only Admin API",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "Question saved successfully",
            content = @Content(mediaType = "application/json"))
    @PostMapping("/save/net-quiz")
    public ResponseEntity<?> saveQuestion(@RequestBody NetQuiz_Questions question) {
        NetQuiz_Questions questions = service.saveQuestion(question);
        return ResponseEntity.status(HttpStatus.OK).body(questions);
    }


    @Operation(summary = "Delete quiz question on .NET", security = @SecurityRequirement(name = "basicAuth"), description = "Only Admin API")
    @ApiResponse(responseCode = "200", description = "Question deleted successfully",
            content = @Content(mediaType = "application/json"))
    @DeleteMapping("admin/net/delete-qs")
    public ResponseEntity<?> deleteQuestion(Long id){
        String res = service.deleteQuestion(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @Operation(summary = "Update quiz question on .NET", security = @SecurityRequirement(name = "basicAuth"), description = "Only Admin API")
    @ApiResponse(responseCode = "200", description = "Question updated successfully",
            content = @Content(mediaType = "application/json"))
    @PatchMapping("/admin/net/update-quiz")
    public ResponseEntity<?> updateQuestions(
            @RequestParam Long id,
            @RequestBody Map<String, Object> updates
    ) {
        NetQuiz_Questions response = service.updateQuestion(updates,id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
