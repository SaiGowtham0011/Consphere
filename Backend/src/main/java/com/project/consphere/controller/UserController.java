package com.project.consphere.controller;

import com.project.consphere.dto.UpdateUserRequest;
import com.project.consphere.model.User;
import com.project.consphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/edit")
    public User updateUser(@RequestBody UpdateUserRequest request) {
        return userService.updateUser(request);
    }

    @DeleteMapping("/delete")
    public User deleteUser(){
        return userService.deleteUser();
    }

}
