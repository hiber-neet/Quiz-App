package com.quizlet.quizapp.controller;

import com.quizlet.quizapp.model.Question;
import com.quizlet.quizapp.model.Answer;
import com.quizlet.quizapp.model.QuestionWrapper;
import com.quizlet.quizapp.service.QuestionService;
import com.quizlet.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    QuizService quizService;

    @GetMapping("/{title}")
    public String getQuestionsByTitle(@PathVariable String title, Model model) {
        QuestionWrapper questionWrapper = questionService.getQuestionByTitle(title);
        model.addAttribute("questionWrapper", questionWrapper);
        return "quiz";
    }

    @PostMapping("/submitAnswer")
    public String getThePointAfterAnswer(@ModelAttribute("questionWrapper") QuestionWrapper questionWrapper){
        List<Answer> answerList = questionWrapper.getAnswerList();
        for (int i = 0; i < answerList.size(); i++){
            System.out.println(answerList.get(i).getAnswerOfUser());
        }
        return "score";
    }

    @GetMapping("allQuestions")
    public List<Question> getAllQuestions(){
        return questionService.getAllQuestions();
    }
//    @GetMapping("category/{category}")
//    public List<Question> getQuestionsByCategory(@PathVariable String category){
//        return questionService.getQuestionsByCategory(category);
//    }
    @GetMapping("difficultlevel/{difficultlevel}")
    public List<Question> getQuestionsByDifficultLevel(@PathVariable String difficultlevel){
        return questionService.getQuestionsByDifficultLevel(difficultlevel);
    }
    @PostMapping("add")
    public String addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @PutMapping("update")
    public String updateQuestion(@RequestBody Question question){
        return questionService.updateQuestion(question);
    }

}
