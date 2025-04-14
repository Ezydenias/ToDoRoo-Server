package com.Roo.demo.controller;

import com.Roo.demo.models.User;
import com.Roo.demo.models.UserRegister;
import com.Roo.demo.service.UserService;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.StringLiteral;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {
    
    @Autowired
    private UserService service;

    
    @PostMapping(value = "/register")
    public ModelAndView register(@ModelAttribute UserRegister user, Model model) throws Exception {
        try {
        var temp = service.register(user);
        }catch (Exception e){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("register.html");
            model.addAttribute("errorMessage", e.getMessage());
            return modelAndView;
        }
        
        return registerSuccess();
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return service.verify(user);
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
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
