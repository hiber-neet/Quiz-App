package com.quizlet.quizapp.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtKey {
    @Value("${jwt.token}")
    private String secretKey;
    public JwtKey(@Value("${jwt.token}") String secretKey) {
        this.secretKey = secretKey;
        System.out.println("secret la ccj? " + secretKey);
    }


}
