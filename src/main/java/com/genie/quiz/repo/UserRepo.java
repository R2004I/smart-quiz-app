package com.genie.quiz.repo;

import com.genie.quiz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Integer> {

    public UserEntity findByEmail(String email);
}
