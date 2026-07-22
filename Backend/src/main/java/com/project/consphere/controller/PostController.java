package com.project.consphere.controller;

import com.project.consphere.model.Post;
import com.project.consphere.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/addPost")
    public Post addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }
}
