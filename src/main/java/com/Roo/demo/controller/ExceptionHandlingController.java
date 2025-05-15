package com.Roo.demo.controller;

import com.Roo.demo.exceptions.RegisterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @ExceptionHandler(RegisterException.class)
    public void UnhingedRegisterError() {
        logger.error("Register Error raised without Handling.");
    }

}

