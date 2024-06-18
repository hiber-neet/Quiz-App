package com.quizlet.quizapp.service;

import com.quizlet.quizapp.model.*;
import com.quizlet.quizapp.repository.QuestionRepository;
import com.quizlet.quizapp.repository.QuizRepository;
import com.quizlet.quizapp.repository.Quiz_QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    Quiz_QuestionsRepository quizQuestionsRepository;
    public List<Question> getAllQuestions(){
        return (List<Question>) questionRepository.findAll();
    }
    public List<Question> getQuestionsByDifficultLevel(String DifficultLevel){
        return questionRepository.findBydifficultyLevel(DifficultLevel);
    }

    public QuestionWrapper getQuestionByTitle(String title){
        QuestionWrapper questionWrapper = new QuestionWrapper();
        Optional<Quiz> quiz = quizRepository.findByTitle(title);
        List<Quiz_Questions> listQuizQuestion = null;
        List<Answer> questionListWrapper = new ArrayList<>();
        int i = 0;
        if(quiz.isPresent()){
            listQuizQuestion = quizQuestionsRepository.findByQuiz_id(quiz.get().getId());
        }
        if(listQuizQuestion != null){
            for (Quiz_Questions quizQuestions : listQuizQuestion) {
                questionListWrapper.add(new Answer(quizQuestions.getQuestion().getQuestionTitle(), quizQuestions.getQuestion().getOption1()
                , quizQuestions.getQuestion().getOption2(), quizQuestions.getQuestion().getOption3(),
                        quizQuestions.getQuestion().getOption4()));
                i++;
            }
        }
        questionWrapper.setAnswerList(questionListWrapper);
        return questionWrapper;
    }

    public String addQuestion(Question question){
        questionRepository.save(question);
        return "success";
    }
    public String updateQuestion(Question question){
        questionRepository.save(question);
        return "success";
    }


}
