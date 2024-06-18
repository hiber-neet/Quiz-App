package com.quizlet.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
    @Column(name = "difficulty_level")
    private String difficultyLevel;
    @OneToMany
    @JoinColumn(name = "question_id")
    List<Quiz_Questions> quizQuestionsList;
//    private String category;


}