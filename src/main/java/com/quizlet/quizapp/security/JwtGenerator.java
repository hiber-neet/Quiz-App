package com.quizlet.quizapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtGenerator {
    @Autowired
    private JwtKey jwtKey;
    SecretKey key;

    public JwtGenerator() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256);
        this.key = keyGen.generateKey();
    }

    public String generateToken(Authentication authentication){
        System.out.println("tao token hoii");
        String userName = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + 70000);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        String token = "";
        try {
            token = Jwts.builder().subject(userName).claim("roles", roles).issuedAt(currentDate).expiration(expireDate).signWith(getSecretKey()).compact();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return token;
    }

    public SecretKey getSecretKey() throws NoSuchAlgorithmException {
//        System.out.println("Secretkey la gi: " + jwtKey.getSecretKey());
//        String secretKeyString = jwtKey.getSecretKey();
        byte[] keyBytes = Base64.getDecoder().decode("Y2hpZW5iaW5oYW5oc2FuZ2Jhb3ZlY29uZ2x5dGhlZ2lvaQ==");
//        for (int i = 0; i < keyBytes.length; i++){
//            System.out.print(keyBytes[i]);
//        }
//        System.out.println();
        SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA256");
//        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//        keyGen.init(256);
//        SecretKey key = keyGen.generateKey();
        System.out.println("Key co thay doi? ko: " + Arrays.toString(key.getEncoded()));
        return key;
    }

    public String getUserNameFromJwt(String token) throws NoSuchAlgorithmException {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch(Exception ex){
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
