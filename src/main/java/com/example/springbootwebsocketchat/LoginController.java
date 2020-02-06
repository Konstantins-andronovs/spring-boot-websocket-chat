package com.example.springbootwebsocketchat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/")
    public String login() {
        return "login";
    }

    @RequestMapping("/chat/{username}")
    public String chat(@PathVariable String username) {
        return "chat";
    }

}
