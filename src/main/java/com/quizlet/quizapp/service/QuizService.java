package com.quizlet.quizapp.service;

import com.quizlet.quizapp.repository.QuestionRepository;
import com.quizlet.quizapp.repository.QuizRepository;
import com.quizlet.quizapp.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuestionRepository questionRepository;

//    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
//        List<Question> questionList = questionDao.findRandomQuestionsByCategory(category, numQ);
//        Quiz quiz = new Quiz();
//        quiz.setTitle(title);
//        quiz.setQuestions(questionList);
//        quizDao.save(quiz);
//        return new ResponseEntity<>("success", HttpStatus.CREATED);
//    }

    public List<Quiz> getQuizList() {
        return (List<Quiz>) quizRepository.findAll();
    }

//    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
//        Optional<Quiz> quiz = quizDao.findById(id);
//        for (int i = 0; i < quiz.get().getQuestions().size(); i++){
//            System.out.println(quiz.get().getQuestions().get(i));
//        }
//        List<Question> questionsFromDB = quiz.get().getQuestions();
//        List<QuestionWrapper> questionsForUser = new ArrayList<>();
//        for(Question q : questionsFromDB){
//            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
//            questionsForUser.add(qw);
//        }
//
//        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
//    }
//    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
//        Quiz quiz = quizDao.findById(id).get();
//        List<Question> questions = quiz.getQuestions();
//        int right = 0;
//        int i = 0;
//        for(Response response : responses){
//            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
//                right++;
//
//            i++;
//        }
//        return new ResponseEntity<>(right, HttpStatus.OK);
//    }
}
