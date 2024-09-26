package com.ssk.SpringSecurityWithDaoAndJwt.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @GetMapping("admin")
    public String admin(){
        return "Hello Admin.. Welcome to app";
    }
    @GetMapping("manager")
    public String manager(){
        return "Hello Manager.. Welcome to app";
    }
    @GetMapping("employee")
    public String employee(){
        return "Hello Employee.. Welcome to app";
    }

}
