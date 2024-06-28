package com.quizlet.quizapp.controller;


import com.quizlet.quizapp.model.Quiz;
import com.quizlet.quizapp.service.QuizService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuizController {
    @Autowired
    QuizService quizService;

    @GetMapping("/")
    public String welcomePage(Model model, HttpServletRequest request){
        List<Quiz> listQuiz = quizService.getQuizList();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT_TOKEN")) {
                    model.addAttribute("JWT_TOKEN", cookie.getName());
                }
            }
        }
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
