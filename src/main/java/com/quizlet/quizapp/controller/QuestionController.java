package com.quizlet.quizapp.controller;

import com.quizlet.quizapp.model.*;
import com.quizlet.quizapp.repository.UserPointRepository;
import com.quizlet.quizapp.repository.UserRepository;
import com.quizlet.quizapp.service.QuestionService;
import com.quizlet.quizapp.service.QuizService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/{title}")
    public String getQuestionsByTitle(@PathVariable String title, Model model) {
        QuestionWrapper questionWrapper = questionService.getQuestionByTitle(title);
        model.addAttribute("questionWrapper", questionWrapper);
        return "quiz";
    }

    @PostMapping("/submitAnswer")
    public String getThePointAfterAnswer(@ModelAttribute("questionWrapper") QuestionWrapper questionWrapper, Model model, HttpServletRequest request) throws NoSuchAlgorithmException {
        return questionService.getPointAfterAnswerQuestions(questionWrapper, model, request);
    }
    @GetMapping("/view/answers")
    public String viewAllAnswer(Model model, HttpServletRequest request) throws NoSuchAlgorithmException {
        System.out.println("ko vao` wtf?????????");
        return questionService.viewAllAnswer(model, request);
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
