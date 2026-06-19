package com.project.consphere.controller;

import com.project.consphere.model.User;
import com.project.consphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/posts")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @
}
