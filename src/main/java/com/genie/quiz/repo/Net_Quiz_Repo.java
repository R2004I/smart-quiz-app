package com.genie.quiz.repo;

import com.genie.quiz.entity.NetQuiz_Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Net_Quiz_Repo extends JpaRepository<NetQuiz_Questions,Long> {
}
