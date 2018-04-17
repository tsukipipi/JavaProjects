package com.pipi.service;

import com.pipi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pipi.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findUserByName(String name){
        User user = null;
        try{
            user = userRepository.findByUserName(name);
        }catch (Exception e){
        }
        return user;
    }

}
