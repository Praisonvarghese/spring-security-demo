package com.programmingbuddy.controller;

import com.programmingbuddy.dto.AuthRequest;
import com.programmingbuddy.entity.UserInfo;
import com.programmingbuddy.repo.UserInfoRepo;
import com.programmingbuddy.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
public class SpringSecurityController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserInfoRepo repo;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "Welcome admin";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user")
    public String user() {
        return "Welcome user";
    }

    @GetMapping("/public")
    public String greetings() {
        return "Welcome everyone";
    }

    @PostMapping("/adduser")
    public UserInfo addUser(@RequestBody UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return repo.save(userInfo);
    }

    @PostMapping("/auth")
    public String authenticate(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authRequest.getUsername());
            } else {
                return "Unauthenticated!!!";
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
