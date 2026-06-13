package com.project.consphere.service;

import com.project.consphere.model.Post;

import java.util.List;

public interface PostService {
    List<Post> findAllPosts(Long userId);
    List<Post> findAllPostsByUserId(Long userId);
}
