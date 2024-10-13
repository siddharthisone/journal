package com.project.myFirstProject.controller;


import com.project.myFirstProject.entity.User;
import com.project.myFirstProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck()
    {
        return "ok";
    }

    @PostMapping("/create-user")
    public void addNewUser(@RequestBody User user)
    {
        userService.saveNewUser(user);
    }
}
