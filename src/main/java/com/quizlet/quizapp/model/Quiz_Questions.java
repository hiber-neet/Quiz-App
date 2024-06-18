package com.quizlet.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(QuizQuestionId.class)
public class Quiz_Questions {
    @Id
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    Quiz quiz;
    @Id
    @ManyToOne
    @JoinColumn(name = "question_id")
    Question question;
}
