package com.quizlet.quizapp.controller;


import com.quizlet.quizapp.model.Quiz;
import com.quizlet.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuizController {
    @Autowired
    QuizService quizService;

    @GetMapping("/")
    public String welcomePage(Model model){
        List<Quiz> listQuiz = quizService.getQuizList();

        model.addAttribute("listQuiz", listQuiz);
        return "welcome";
    }

//    @PostMapping("create")
//    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
//        return quizService.createQuiz(category, numQ, title);
//    }
//    @GetMapping("get/{id}")
//    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(@PathVariable int id){
//        return quizService.getQuizQuestions(id);
//    }
}
