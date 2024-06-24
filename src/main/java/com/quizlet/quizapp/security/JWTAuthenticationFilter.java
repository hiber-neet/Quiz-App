package com.quizlet.quizapp.security;

import com.quizlet.quizapp.service.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = extractTokenFromCookie(request);
        System.out.println("token from request: " + token);
        System.out.println("internal filter chain");
        if(StringUtils.hasText(token) && jwtGenerator.validateToken(token)){
            System.out.println("ok, validate thanh cong token");
            String userName = null;
            try {
                userName = jwtGenerator.getUserNameFromJwt(token);
                System.out.println("User name laccj:" + userName);
            } catch (NoSuchAlgorithmException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
            UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
            System.out.println(userDetails.getAuthorities()+ "trong getAuthorities cua? userDetails co j");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT_TOKEN")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    //Stand for api using ResponeEnitty.OK().header
    private String getJWTFromRequestForApi(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        System.out.println("khong co token vi la lan dau tien");
        return null;
    }
}
