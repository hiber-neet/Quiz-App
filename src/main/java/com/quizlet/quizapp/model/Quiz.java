package com.quizlet.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Integer id;
    private String title;

    @OneToMany()
    @JoinColumn(name = "quiz_id")
    private List<Quiz_Questions> questions;


}
