package com.felixlm.usersmodule.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("users/hello")
    public String hello(){
        return "Users module working";
    }
}
