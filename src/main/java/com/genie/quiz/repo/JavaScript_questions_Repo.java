package com.genie.quiz.repo;

import com.genie.quiz.entity.JavaScript_questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JavaScript_questions_Repo extends JpaRepository<JavaScript_questions, Long> {
}
