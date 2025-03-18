package com.genie.quiz.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "NET-quiz")
public class NetQuiz_Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quizQuestionText;

    private String quizAnswer;

    @ElementCollection
    @CollectionTable(name = "Net-option-table", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "Net-Options")
    private List<String> quizOptions;

    public NetQuiz_Questions() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuizQuestionText() {
        return quizQuestionText;
    }

    public void setQuizQuestionText(String quizQuestionText) {
        this.quizQuestionText = quizQuestionText;
    }

    public String getQuizAnswer() {
        return quizAnswer;
    }

    public void setQuizAnswer(String quizAnswer) {
        this.quizAnswer = quizAnswer;
    }

    public List<String> getQuizOptions() {
        return quizOptions;
    }

    public void setQuizOptions(List<String> quizOptions) {
        this.quizOptions = quizOptions;
    }
}
