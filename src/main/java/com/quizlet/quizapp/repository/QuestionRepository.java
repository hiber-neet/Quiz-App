package com.quizlet.quizapp.repository;

import com.quizlet.quizapp.model.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {

    List<Question> findBydifficultyLevel(String difficulty_level);


//
//    @Query(value = "SELECT * FROM question q Where q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
//    List<Question> findRandomQuestionsByCategory(String category, int numQ);

}