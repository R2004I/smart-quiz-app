package com.genie.quiz.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "javaScript-quizes")
public class JavaScript_questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String javaScript_Question;

    private String javaScript_correct_option;


    @ElementCollection
    @CollectionTable(name = "javaScript-options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "javaScript_options")
    private List<String> javaScript_options;

    public JavaScript_questions() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJavaScript_Question() {
        return javaScript_Question;
    }

    public void setJavaScript_Question(String javaScript_Question) {
        this.javaScript_Question = javaScript_Question;
    }

    public String getJavaScript_correct_option() {
        return javaScript_correct_option;
    }

    public void setJavaScript_correct_option(String javaScript_correct_option) {
        this.javaScript_correct_option = javaScript_correct_option;
    }

    public List<String> getJavaScript_options() {
        return javaScript_options;
    }

    public void setJavaScript_options(List<String> javaScript_options) {
        this.javaScript_options = javaScript_options;
    }
}
