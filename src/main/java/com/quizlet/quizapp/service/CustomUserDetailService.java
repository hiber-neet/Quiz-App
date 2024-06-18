package com.quizlet.quizapp.service;

import com.quizlet.quizapp.model.Role;
import com.quizlet.quizapp.model.UserEntity;
import com.quizlet.quizapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Vao custom login service");
        UserEntity userEntity = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("UserName Not Found!!!"));
        return new User(userEntity.getUserName(), userEntity.getPassword(), mapRoleToGrantedAuthority(userEntity.getRoles()));
    }
    public Collection<GrantedAuthority> mapRoleToGrantedAuthority(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
