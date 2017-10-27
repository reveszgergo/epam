package com.example.epam.epam.authentication;

import com.example.epam.epam.CustomErrorType;
import com.example.epam.epam.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/authentication")
public class AuthenticationController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private UserRepository userRepository;
    private UserDetailsService userDetailsService;

    @Autowired
    public AuthenticationController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, UserRepository userRepository, UserDetailsService userDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<?> newUser(@RequestBody User user){

        User userExists = userService.findByUsername(user.getUsername());

        if(userExists != null){
            return new ResponseEntity<>(new CustomErrorType("Unable to create. User with username " + user.getUsername() + " already exists."),
                    HttpStatus.CONFLICT);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setEnabled(true);

        user.setToken(UUID.randomUUID().toString());

        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody String username, String password){
        return new ResponseEntity<>(userDetailsService.loadUserByUsername(username), HttpStatus.OK);
    }

}
