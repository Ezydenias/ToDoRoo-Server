package com.Roo.demo.service;

import com.Roo.demo.models.User;
import com.Roo.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;
    
    @Autowired
    private JWTService jwtService;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    
    public User register(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        
       return authentication.isAuthenticated() ? jwtService.generateToken(user.getUsername()) : "fail";
    }
}
