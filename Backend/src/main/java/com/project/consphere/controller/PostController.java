package com.project.consphere.controller;

import com.project.consphere.dto.CreatePostRequest;
import com.project.consphere.dto.PostResponse;
import com.project.consphere.dto.UpdatePostRequest;
import com.project.consphere.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest request) {
        PostResponse response = postService.createPost(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/my-posts")
    public ResponseEntity<List<PostResponse>> getMyPosts() {
        List<PostResponse> response = postService.getMyPosts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable String username) {
        List<PostResponse> response = postService.getUserPosts(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostResponse>> getHomeFeed() {
        List<PostResponse> response = postService.getHomeFeed();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest request) {
        PostResponse response = postService.updatePost(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
