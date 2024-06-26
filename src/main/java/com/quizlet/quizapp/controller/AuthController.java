package com.quizlet.quizapp.controller;

import com.quizlet.quizapp.dto.LoginAccountDTO;
import com.quizlet.quizapp.dto.RegistrationDTO;
import com.quizlet.quizapp.model.Role;
import com.quizlet.quizapp.model.UserEntity;
import com.quizlet.quizapp.repository.RoleRepository;
import com.quizlet.quizapp.repository.UserRepository;
import com.quizlet.quizapp.security.JwtGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDTO registrationDTO) {
        if (userRepository.existsByUserName(registrationDTO.getUsername())) {
            return new ResponseEntity<>("Account is taken!", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUserName(registrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        Role roles = roleRepository.findByName("User").get();
        user.setRoles(Collections.singletonList(roles));
        userRepository.save(user);
        return new ResponseEntity<>("Registration Success!!", HttpStatus.OK);
    }

    //spring security lam sao de gui toi thg login nay`
    @PostMapping("/login")
    public String login(@ModelAttribute LoginAccountDTO loginAccountDTO, HttpServletResponse response) {
        System.out.println("vao login khong? ngay cong?");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginAccountDTO.getUsername(), loginAccountDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("vao login de tao token?");
        String token = jwtGenerator.generateToken(authentication);
        // Add token to cookie
        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setPath("/"); // Set cookie path, adjust if necessary
        cookie.setHttpOnly(true); // Ensure cookie is accessible only via HTTP
        cookie.setMaxAge(24 * 60 * 60); // Set cookie expiration time in seconds, adjust as needed
        response.addCookie(cookie);
        System.out.println("token response`: " + token);
        return "redirect:/";
    }

    @GetMapping("/login")
        public String loginForm(){
            return "login";
        }
}




