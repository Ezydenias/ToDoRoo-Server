package com.Roo.demo.service;

import com.Roo.demo.exceptions.RegisterException;
import com.Roo.demo.models.User;
import com.Roo.demo.models.UserRegister;
import com.Roo.demo.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import static com.Roo.demo.validators.RooEmailValidator.isValidEmail;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Value("${pepper}")
    private String pepper;

    String regExpn = "^(?=.*[0-9])(?=.*\\d):(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$)";
    String regExpnLenngth = "^{8,}$";
    
    private Pbkdf2PasswordEncoder encoder;

    @PostConstruct
    private void InitializeEncoder(){
        encoder = new Pbkdf2PasswordEncoder(pepper,5,2000,256);
    }
    
    public User register(UserRegister user) throws Exception {
        if (user.getUsername().isEmpty())
            throw new RegisterException("Please Provide a username");
        if (user.getPassword().isEmpty())
            throw new RegisterException("Please Provide a Password");
        if (repo.findByUsername(user.getUsername()) != null)
            throw new RegisterException("Username already taken");

        if (!user.getPassword().equals(user.getRepeatpassword()))
            throw new RegisterException("Password needs to be repeated correctly");
        if (user.getEmail().isEmpty())
            throw new RegisterException("Please Provide an E-mail");
        if (!isValidEmail(user.getEmail()))
            throw new RegisterException("Invalid E-mail address");

        if(!Pattern.compile(regExpnLenngth, Pattern.CASE_INSENSITIVE).matcher(user.getPassword()).matches())
            throw new RegisterException("Password needs to be at least 8 symbols long");        
        if(!Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE).matcher(user.getPassword()).matches())
            throw new RegisterException("Needs at least one special character, a Number and Letter");

        user.setId(repo.getMaxId() + 1);
        
        user.setPassword(encoder.encode(user.getPassword()));

        if (user.getId() == 0)
            throw new RegisterException("Registration Error");

        return repo.save(new User(user));
    }

    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        return authentication.isAuthenticated() ? jwtService.generateToken(user.getUsername()) : "fail";
    }
}
