package com.ssk.SpringSecurityWithDaoAndJwt.rest;

import com.ssk.SpringSecurityWithDaoAndJwt.model.User;
import com.ssk.SpringSecurityWithDaoAndJwt.service.JwtService;
import com.ssk.SpringSecurityWithDaoAndJwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authManager, JwtService jwtService) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @GetMapping("home")
    public String greet(){
        return "Hello Welcome to App";
    }

    @PostMapping("register")
    public User register(@RequestBody User user){
        return userService.createUser(user);
    }

    @PostMapping("login")
    public String login(@RequestBody User user) throws Exception {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if(auth.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "Login Failed";
    }
}
