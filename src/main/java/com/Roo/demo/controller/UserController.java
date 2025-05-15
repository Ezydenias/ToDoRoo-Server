package com.Roo.demo.controller;

import com.Roo.demo.exceptions.RegisterException;
import com.Roo.demo.models.User;
import com.Roo.demo.dto.UserRegisterDto;
import com.Roo.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {
    
    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private void SetNonce(Model model, HttpServletRequest request) {
//        model.addAttribute("nonce", request.getAttribute("nonce"));
//        model.addAttribute("nonceStyle", request.getAttribute("nonceStyle"));
    }
    
    @PostMapping(value = "/register")
    public ModelAndView register(@ModelAttribute UserRegisterDto user, Model model, HttpServletRequest request) throws Exception {
        try {
        service.register(user);
        }catch (RegisterException e){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("register.html");
            model.addAttribute("errorMessage", e.getMessage());
            SetNonce(model, request);
            return modelAndView;
        }
        
        return registerSuccess(model, request);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return service.verify(user);
    }

    @GetMapping("/login")
    public ModelAndView login(Model model, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        SetNonce(model, request);
        return modelAndView;
    }

    @GetMapping("/logoutPage")
    public ModelAndView logout(Model model, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("logout.html");
        SetNonce(model, request);
        return modelAndView;
    }

    @GetMapping("/registerPage")
    public ModelAndView register(Model model, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register.html");
        SetNonce(model, request);
        return modelAndView;
    }

    @GetMapping("/registerSuccess")
    public ModelAndView registerSuccess(Model model, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registerSuccess.html");
        SetNonce(model, request);
        return modelAndView;
    }
    
   
}
