package com.Roo.demo.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginConntroller {


//    @GetMapping("/Welcome")
//    public ResponseEntity<String> login() {
//        return ResponseEntity.ok("<h1>Welcome</h1>");
//    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
