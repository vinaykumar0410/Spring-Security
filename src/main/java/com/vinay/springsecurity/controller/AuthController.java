package com.vinay.springsecurity.controller;

import com.vinay.springsecurity.dto.LoginRequest;
import com.vinay.springsecurity.dto.TokenResponse;
import com.vinay.springsecurity.model.User;
import com.vinay.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/home")
    public String home(){
        return "Welcome to Spring Security";
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
            @RequestBody User user
    ){
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(
            @RequestBody LoginRequest loginRequest
            ){
        String token = userService.loginUser(loginRequest);
        TokenResponse tokenResponse = new TokenResponse(token, "Login SuccessFul");
        return new ResponseEntity<>(tokenResponse,HttpStatus.OK);
    }

}
