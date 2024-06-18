package com.quizlet.quizapp.repository;

import com.quizlet.quizapp.model.QuizQuestionId;
import com.quizlet.quizapp.model.Quiz_Questions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Quiz_QuestionsRepository extends CrudRepository<Quiz_Questions, QuizQuestionId> {
    List<Quiz_Questions> findByQuiz_id(int id);
}
