package com.vinay.springsecurity.service.userserviceimpl;

import com.vinay.springsecurity.dto.LoginRequest;
import com.vinay.springsecurity.model.User;
import com.vinay.springsecurity.repository.UserRepository;
import com.vinay.springsecurity.security.JwtProvider;
import com.vinay.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public User registerUser(User user) {
        if(user.getName()==null){
            throw new BadCredentialsException("User Name Not Found");
        }
        if(user.getEmail()==null){
            throw new BadCredentialsException("Email Not Found");
        }
        if(user.getPassword()==null){
            throw new BadCredentialsException("Password Not Found");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public String loginUser(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if(userDetails == null){
            throw new BadCredentialsException("Invalid UserName");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Password mismatch");
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(email,password);
        return JwtProvider.generateJwtToken(auth);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
