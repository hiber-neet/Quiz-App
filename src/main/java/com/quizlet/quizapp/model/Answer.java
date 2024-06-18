package com.quizlet.quizapp.model;

import lombok.Data;

@Data
public class Answer {

    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answerOfUser;
    public Answer(String questionTitle, String option1, String option2, String option3, String option4) {
        this.questionTitle = questionTitle;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;

    }
    public Answer(){

    }
}
