package com.project.consphere.controller;

import com.project.consphere.dto.LikeResponse;
import com.project.consphere.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<LikeResponse> likePost(@PathVariable Long postId) {
        LikeResponse response = likeService.likePost(postId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<LikeResponse> unlikePost(@PathVariable Long postId) {
        LikeResponse response = likeService.unlikePost(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<LikeResponse> getLikeStatus(@PathVariable Long postId) {
        LikeResponse response = likeService.getLikeStatus(postId);
        return ResponseEntity.ok(response);
    }
}
