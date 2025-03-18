package com.genie.quiz.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genie.quiz.dto.CustomQuizDTO;
import com.genie.quiz.dto.QuizRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CustomQuizService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String GEMINI_API_URL;

    @Value("${gemini.api.key}")
    private String GEMINI_API_KEY;

    public CustomQuizService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    private String buildPrompt2(QuizRequest req){
        StringBuilder builder = new StringBuilder();
        builder.append("Generate a multiple-choice quiz questions on the topic: ").
                append("topic is"+req.getTopic()).
                append("You have to strictly follows this response format.").
                append("Format is : ").
                append("{\"questionText\":\"Actualquestiontext\",\"correctAnswer\":\"Correctanswer\",\"options\":[\"option1\",\"option2\",\"option3\",\"option4\"]},");
        return builder.toString();
    }

    public List<CustomQuizDTO> generateQuiz(QuizRequest request){
        String prompt = buildPrompt2(request);
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );
        // Call AI API
        String response = webClient.post()
                .uri(GEMINI_API_URL + GEMINI_API_KEY)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(res -> System.out.println("Gemini API Response: " + res))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                .block();

        return extractQuizData(response);
    }

    private List<CustomQuizDTO> extractQuizData(String response) {
        JsonNode questionNode = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            String textResponse = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            // Remove Markdown formatting if present
            if (textResponse.startsWith("```json")) {
                textResponse = textResponse.substring(7); // Remove "```json\n"
            }
            if (textResponse.endsWith("```")) {
                textResponse = textResponse.substring(0, textResponse.length() - 3); // Remove trailing "```"
            }


            // Convert extracted JSON text into a JsonNode object
            questionNode = mapper.readTree(textResponse);
        } catch (Exception e) {
            e.getMessage();
        }

        return formatJson(questionNode);
    }

    private List<CustomQuizDTO> formatJson(JsonNode jsonResponse) {
        List<CustomQuizDTO> quizList = new ArrayList<>();
        try {
            if (jsonResponse.isArray()) {
                for (JsonNode node : jsonResponse) {
                    CustomQuizDTO quiz = new CustomQuizDTO();

                    // Extract question
                    quiz.setQuestion(node.path("questionText").asText());

                    // Extract options
                    List<String> options = new ArrayList<>();
                    node.path("options").forEach(option -> options.add(option.asText()));
                    quiz.setOptions(options);

                    // Extract correct answer
                    quiz.setCorrectAns(node.path("correctAnswer").asText());

                    quizList.add(quiz);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quizList;

    }


}
