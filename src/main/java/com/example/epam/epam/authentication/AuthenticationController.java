package com.example.epam.epam.authentication;

import com.example.epam.epam.CustomErrorType;
import com.example.epam.epam.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/authentication")
public class AuthenticationController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<?> newUser(@RequestBody User user){

        User userExists = userRepository.findUserByUsername(user.getUsername());

        if(userExists != null){
            return new ResponseEntity<>(new CustomErrorType("Unable to create. User with username " + user.getUsername() + " already exists."),
                    HttpStatus.CONFLICT);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        List<String > roles = new ArrayList<>();
        roles.add("ROLE_USER");
        user.setRoles(roles);

        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, HttpServletResponse response) throws IOException{

        String token = null;
        User user = userRepository.findUserByUsername(username);
        Map<String, Object> tokenMap = new HashMap<>();
        if(user != null && bCryptPasswordEncoder.matches(password, user.getPassword())){
            token = Jwts.builder().setSubject(username).claim("roles", user.getRoles()).setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
            tokenMap.put("token", token);
            tokenMap.put("user", user);
            return new ResponseEntity<>(tokenMap, HttpStatus.OK);
        }else{
            tokenMap.put("token", null);
            return new ResponseEntity<>(tokenMap, HttpStatus.UNAUTHORIZED);
        }
    }



}
