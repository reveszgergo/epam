package com.example.epam.epam.authentication;

import com.example.epam.epam.authentication.User;
import com.example.epam.epam.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    public User findByToken(String token){
        return userRepository.findByToken(token);
    }

}
