package com.genie.quiz.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "python-quizes")
public class PythonQuestions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String python_questions;
    private String py_correct_ans;

    @ElementCollection
    @CollectionTable(name = "python_questions", joinColumns = @JoinColumn(name = "question-id"))
    @Column(name = "python-questions")
    private List<String> py_options;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPython_questions() {
        return python_questions;
    }

    public void setPython_questions(String python_questions) {
        this.python_questions = python_questions;
    }

    public String getPy_correct_ans() {
        return py_correct_ans;
    }

    public void setPy_correct_ans(String py_correct_ans) {
        this.py_correct_ans = py_correct_ans;
    }

    public List<String> getPy_options() {
        return py_options;
    }

    public void setPy_options(List<String> py_options) {
        this.py_options = py_options;
    }
}
