package com.project.consphere.service;

import com.project.consphere.dto.CreatePostRequest;
import com.project.consphere.dto.PostResponse;
import com.project.consphere.dto.UpdatePostRequest;

import java.util.List;

public interface PostService {

    PostResponse createPost(CreatePostRequest request);

    List<PostResponse> getMyPosts();

    List<PostResponse> getUserPosts(String username);

    PostResponse updatePost(Long id, UpdatePostRequest request);

    void deletePost(Long id);

    List<PostResponse> getHomeFeed();

    PostResponse getPostById(Long id);
}
