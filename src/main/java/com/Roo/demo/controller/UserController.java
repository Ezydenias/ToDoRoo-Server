package com.Roo.demo.controller;

import com.Roo.demo.exceptions.RegisterException;
import com.Roo.demo.models.User;
import com.Roo.demo.models.UserRegister;
import com.Roo.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.mapping.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.spel.ast.StringLiteral;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {
    
    @Autowired
    private UserService service;

    Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @PostMapping(value = "/register")
    public ModelAndView register(@ModelAttribute UserRegister user, Model model, HttpServletRequest request) throws Exception {
        try {
        service.register(user);
        }catch (RegisterException e){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("register.html");
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("nonce", request.getAttribute("nonce"));
            return modelAndView;
        }
        
        return registerSuccess();
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return service.verify(user);
    }

    @GetMapping("/login")
    public ModelAndView login(Model model, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        model.addAttribute("nonce", request.getAttribute("nonce"));
        return modelAndView;
    }

    @GetMapping("/logoutPage")
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("logout.html");
        return modelAndView;
    }

    @GetMapping("/registerPage")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register.html");
        return modelAndView;
    }

    @GetMapping("/registerSuccess")
    public ModelAndView registerSuccess() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registerSuccess.html");
        return modelAndView;
    }
}
