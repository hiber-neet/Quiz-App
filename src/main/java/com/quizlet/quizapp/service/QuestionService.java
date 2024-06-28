package com.quizlet.quizapp.service;

import com.quizlet.quizapp.model.*;
import com.quizlet.quizapp.repository.*;
import com.quizlet.quizapp.security.JwtGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.NoSuchAlgorithmException;
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
    @Autowired
    private UserPointRepository userPointRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtGenerator jwtGenerator;

    public List<Question> getAllQuestions(){
        return (List<Question>) questionRepository.findAll();
    }
    public List<Question> getQuestionsByDifficultLevel(String DifficultLevel){
        return questionRepository.findBydifficultyLevel(DifficultLevel);
    }
    public String viewAllAnswer(Model model, HttpServletRequest request) throws NoSuchAlgorithmException {
        Cookie[] cookies = request.getCookies();
        String userName = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT_TOKEN")) {
                    userName = jwtGenerator.getUserNameFromJwt(cookie.getValue());
                    UserPoint userPoint = new UserPoint();
                    Optional<UserEntity> user = userRepository.findByUserName(userName);
                    List<UserPoint> answerList = userPointRepository.findByUserId(user.get().getId());
                    model.addAttribute("answerList", answerList);
                }
            }
        }
        return "viewAllPoint";
    }
    public String getPointAfterAnswerQuestions(QuestionWrapper questionWrapper, Model model, HttpServletRequest request) throws NoSuchAlgorithmException {
        List<Answer> answerList = questionWrapper.getAnswerList();
        int point = 0;
        for (int i = 0; i < answerList.size(); i++){
            try{
                Question question = getQuestionById(answerList.get(i).getId());
                if(answerList.get(i).getAnswerOfUser().equals(question.getRightAnswer())){
                    point++;
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        String userName = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT_TOKEN")) {
                    userName = jwtGenerator.getUserNameFromJwt(cookie.getValue());
                    System.out.println(userName + "userName");
                    model.addAttribute("point", point);
                    UserPoint userPoint = new UserPoint();
                    Optional<UserEntity> user = userRepository.findByUserName(userName);
                    System.out.println(user.get().getUserName());
                    user.ifPresent(userPoint::setUserEntity);
                    userPoint.setPoint(point);
                    userPointRepository.save(userPoint);
                    return "score";
                }
            }
        }

        model.addAttribute("point", point);
        return "score";
    }

    public Question getQuestionById(Integer id) throws ClassNotFoundException {
        Optional<Question> question = questionRepository.findById(id);
        return question.orElseThrow(() -> new ClassNotFoundException("Not Found by id: " + id));
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
                questionListWrapper.add(new Answer(quizQuestions.getQuestion().getId(), quizQuestions.getQuestion().getQuestionTitle(), quizQuestions.getQuestion().getOption1()
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
