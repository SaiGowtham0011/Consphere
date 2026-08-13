package com.project.consphere.controller;

import com.project.consphere.dto.UpdateUserRequest;
import com.project.consphere.dto.UserProfileResponse;
import com.project.consphere.dto.UserResponse;
import com.project.consphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUserProfile() {
        UserResponse response = userService.getCurrentUserProfile();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/updateUser")
    public ResponseEntity<UserResponse> updateUserProfile(@RequestBody UpdateUserRequest request) {
        UserResponse response = userService.updateUserProfile(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserProfileResponse> getUserProfileByUsername(@PathVariable String username) {
        UserProfileResponse response = userService.getUserProfileByUsername(username);
        return ResponseEntity.ok(response);
    }
}
