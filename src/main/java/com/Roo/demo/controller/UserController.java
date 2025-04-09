package com.Roo.demo.controller;

import com.Roo.demo.models.User;
import com.Roo.demo.service.UserService;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.StringLiteral;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {
    
    @Autowired
    private UserService service;
    
    @PostMapping(value = "/register", consumes = {"application/json"})
    public User register(@RequestBody User user){
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return service.verify(user);
    }

    ////@GetMapping("/login")
    ////public ModelAndView login() {
    ////    ModelAndView modelAndView = new ModelAndView();
    ////    modelAndView.setViewName("login.html");
    ////    return modelAndView;
    ////}
}
