package com.example.demo.Controller;


import com.example.demo.Entity.User;
import com.example.demo.Services.Authservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class Authcontroller {

    @Autowired
    private Authservices authservices;


    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authservices.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String ,String> credentials){
        return authservices.login(credentials.get("email"),credentials.get("password"));
    }

}
