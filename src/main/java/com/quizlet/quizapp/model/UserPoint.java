package com.quizlet.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUserPoint")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
    private int point;
}
