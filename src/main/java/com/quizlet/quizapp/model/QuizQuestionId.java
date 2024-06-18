package com.quizlet.quizapp.model;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;
@Data
public class QuizQuestionId implements Serializable {

    private Quiz quiz;
    private Question question;
}
