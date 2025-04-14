package com.Roo.demo.service;

import com.Roo.demo.models.User;
import com.Roo.demo.models.UserRegister;
import com.Roo.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.Roo.demo.validators.RooEmailValidator.isValidEmail;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public User register(UserRegister user) throws Exception {
        if (user.getUsername().isEmpty())
            throw new Exception("Please Provide a username");
        if (user.getPassword().isEmpty())
            throw new Exception("Please Provide a Password");
        if (repo.findByUsername(user.getUsername()) != null)
            throw new Exception("Username already taken");
        
        if (!user.getPassword().equals(user.getRepeatpassword()))
            throw new Exception("Password needs to be repeated correctly");
        if (user.getEmail().isEmpty())
            throw new Exception("Please Provide an E-mail");
        var temp = isValidEmail(user.getEmail());
        if (!isValidEmail(user.getEmail()))
            throw new Exception("Invalid E-mail address");
        
        user.setId(repo.getMaxId() + 1);
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getId() == 0)
            throw new Exception("Registration Error");
        
        return repo.save(new User(user));
    }



    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        return authentication.isAuthenticated() ? jwtService.generateToken(user.getUsername()) : "fail";
    }
}
