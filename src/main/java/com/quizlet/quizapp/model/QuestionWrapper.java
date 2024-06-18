package com.quizlet.quizapp.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Component
public class QuestionWrapper {
    private List<Answer> answerList;
    public List<Answer> getQuestions() {
        return answerList;
    }

    public void setQuestions(List<Answer> answerList) {
        this.answerList = answerList;
    }
}
