package com.project.consphere.service;

import com.project.consphere.model.*;
import com.project.consphere.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private FollowRepository followRepository;

    @Override
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post deletePost(Post post) {
        postRepository.delete(post);
        return post;
    }

    @Override
    public List<Post> getFeedByUserId(User follower){
        List<Follow> followingUsers = followRepository.findByFollower(follower);
        List<Post> posts = postRepository.findAllByUserId(follower.getId());
        if(posts.isEmpty()){
            throw new RuntimeException("Cannot find any posts ");
        }
        return posts;
    }

    @Override
    public List<Post> findAllPostsByUserId(Long userId){
        List<Post> posts = postRepository.findAllByUserId(userId);
        if(posts.isEmpty()){
            System.out.println("Cannot find any posts by userId " + userId);
        }
        return posts;
    }

    @Override
    public List<Comment> getAllCommentsByPost(Post post){
        List<Comment> comments = commentRepository.findAllByPost(post);
        if(comments.isEmpty()){
            System.out.println("Cannot find any comments");
        }
        return comments;
    }

    @Override
    public List<Like> getAllLikesByPost(Post post){
        List<Like> likes = likeRepository.findAllByPost(post);
        if(likes.isEmpty()){
            System.out.println("0 likes");
        }
        return likes;
    }
}
