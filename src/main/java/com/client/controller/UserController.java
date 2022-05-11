/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.controller;

import com.client.model.Message;
import com.client.model.Response;
import com.client.model.UserDto;
import com.client.model.User;
import com.client.respository.UserRepository;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.TimeZone;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author leonard
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private SessionController sessionController;
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping
    public ResponseEntity login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        UserDto userDto = userRepository.findByEmailAddress(username);
        if (userDto != null && password.equals(userDto.getPassword())) {            
            String last_login = Optional.ofNullable(userDto.getLastLogin()).orElse("Never Logged In");
            User logged_user = new User(username, true, last_login);
            saveLastLogin(userDto);
            sessionController.setLoggedInUser(logged_user);
            return ResponseEntity.status(HttpStatus.OK).body(logged_user);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new Response(100, "Invalid Login credentials."));
        }
    }        
    
    @GetMapping
    public ResponseEntity getUser() {
        User session_user = sessionController.getLoggedInUser(true);
        return ResponseEntity.status(HttpStatus.OK).body(session_user);
    }
    
    @GetMapping("/logout")
    public ResponseEntity logoutUser() {
        HttpSession session = sessionController.getSession();
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body(new Response(0, "Logout successful."));
    }
    
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserDto signUpDto) {
        String phone = signUpDto.getPhoneNumber();
        String email = signUpDto.getEmailAddress();
        
        if (userRepository.findByPhoneNumber(phone) != null || userRepository.findByEmailAddress(email) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Response(105, "User Already registered!"));
        } else {
            UserDto reg = userRepository.save(signUpDto);
            Response res = (reg == null) ? new Response(110, "User registration failed!") : new Response(0, "User registration successful.");
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }        
    }
    
    @PostMapping("/contact-us")
    public ResponseEntity contactUs(@RequestBody Message message) {
        System.out.println(message.toString());
        Response res = new Response(0, "Message sent successful.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    
    private void saveLastLogin(UserDto userDto) {
        String date_time = generateTimestamp("yyyy-MM-dd HH:mm:ss");
        userDto.setLastLogin(date_time);
        
        userRepository.save(userDto);
    }
    
    private String generateTimestamp(String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        f.setTimeZone(TimeZone.getTimeZone("EAT"));
        java.util.Date d = new java.util.Date();
        String ts = f.format(d);

        return ts;
    }
    
}
