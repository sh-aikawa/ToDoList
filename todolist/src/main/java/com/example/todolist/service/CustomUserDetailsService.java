package com.example.todolist.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.todolist.model.User;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
/* 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.selectUserByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }
    */
    @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("検索ユーザー: " + username);
    User user = userRepository.selectUserByUsername(username);
    if (user == null) {
        throw new UsernameNotFoundException(username);
    }
    System.out.println("ユーザー取得成功: " + user.getUsername());
    return new CustomUserDetails(user);
}

}
