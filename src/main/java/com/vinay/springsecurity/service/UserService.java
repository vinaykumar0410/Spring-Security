package com.vinay.springsecurity.service;

import com.vinay.springsecurity.dto.LoginRequest;
import com.vinay.springsecurity.model.User;

public interface UserService {

    public User registerUser(User user);

    public String loginUser(LoginRequest loginRequest);

    public User findByEmail(String email);

}
