package com.project.consphere.controller;

import com.project.consphere.dto.FollowResponse;
import com.project.consphere.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/{username}")
    public ResponseEntity<Void> followUser(@PathVariable String username) {
        followService.followUser(username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> unfollowUser(@PathVariable String username) {
        followService.unfollowUser(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followers")
    public ResponseEntity<List<FollowResponse>> getFollowers() {
        List<FollowResponse> response = followService.getFollowers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/following")
    public ResponseEntity<List<FollowResponse>> getFollowing() {
        List<FollowResponse> response = followService.getFollowing();
        return ResponseEntity.ok(response);
    }
}
