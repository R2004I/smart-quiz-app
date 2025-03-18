package com.genie.quiz.controller;


import com.genie.quiz.dto.CustomQuizDTO;
import com.genie.quiz.dto.QuizRequest;
import com.genie.quiz.service.CustomQuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@Tag(name = "Quiz Controller", description = "APIs for generating quiz dynamically using AI")
public class CustomQuizController {

    private final CustomQuizService service;

    public CustomQuizController(CustomQuizService service) {
        this.service = service;
    }


    @Operation(summary = "Generate quiz", description = "Generate quiz based on your provided topic",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponse(responseCode = "200", description = "Generated successfully",
            content = @Content(mediaType = "application/json"))
    @PostMapping("/generate")
    public List<CustomQuizDTO> generateQuiz(@RequestBody QuizRequest topic) {
        return service.generateQuiz(topic);
    }
}
