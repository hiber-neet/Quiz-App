package com.quizlet.quizapp.repository;

import com.quizlet.quizapp.model.Quiz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface QuizRepository extends CrudRepository<Quiz, Integer> {
    Optional<Quiz> findByTitle(String title);
}
