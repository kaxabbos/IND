package com.ind.cont;

import com.ind.cont.global.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginCont extends Attributes {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
