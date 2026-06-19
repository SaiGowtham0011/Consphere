package com.project.consphere.controller;

import com.project.consphere.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
}
