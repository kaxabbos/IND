package com.ind.cont;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginCont extends Global {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
