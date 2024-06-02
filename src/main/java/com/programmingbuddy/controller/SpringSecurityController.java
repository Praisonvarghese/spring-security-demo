package com.programmingbuddy.controller;

import com.programmingbuddy.entity.UserInfo;
import com.programmingbuddy.repo.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
public class SpringSecurityController {

    @Autowired
    private UserInfoRepo repo;

    @Autowired
    PasswordEncoder encoder;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin(){
        return "Welcome admin";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user")
    public String user(){
        return "Welcome user";
    }

    @GetMapping("/public")
    public String greetings(){
        return "Welcome everyone";
    }

    @PostMapping("/adduser")
    public UserInfo addUser(@RequestBody UserInfo userInfo){
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        return repo.save(userInfo);
    }
}
