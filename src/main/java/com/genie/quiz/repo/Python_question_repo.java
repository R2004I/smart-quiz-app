package com.genie.quiz.repo;

import com.genie.quiz.entity.PythonQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Python_question_repo extends JpaRepository<PythonQuestions,Long> {
}
