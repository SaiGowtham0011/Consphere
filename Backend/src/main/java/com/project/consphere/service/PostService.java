package com.project.consphere.service;

import com.project.consphere.model.Post;
import com.project.consphere.model.Comment;
import com.project.consphere.model.Like;
import com.project.consphere.model.User;

import java.util.List;

public interface PostService {
    Post addPost(Post post);
    Post updatePost(Post post);
    Post deletePost(Post post);

    List<Post> getFeedByUserId(User follower);
    List<Post> findAllPostsByUserId(Long userId);
    List<Comment> getAllCommentsByPost(Post post);
    List<Like> getAllLikesByPost(Post post);
}
