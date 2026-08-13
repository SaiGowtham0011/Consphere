package com.project.consphere.service;

import com.project.consphere.dto.LikeResponse;
import com.project.consphere.exception.BadRequestException;
import com.project.consphere.exception.ResourceNotFoundException;
import com.project.consphere.model.Like;
import com.project.consphere.model.Post;
import com.project.consphere.model.User;
import com.project.consphere.repository.LikeRepository;
import com.project.consphere.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public LikeResponse likePost(Long postId) {
        User currentUser = userService.getCurrentUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));

        if (likeRepository.existsByUserAndPost(currentUser, post)) {
            throw new BadRequestException("You have already liked this post");
        }

        Like like = Like.builder()
                .user(currentUser)
                .post(post)
                .build();

        likeRepository.save(like);

        long likeCount = likeRepository.countByPostId(postId);

        return LikeResponse.builder()
                .postId(postId)
                .likeCount(likeCount)
                .liked(true)
                .build();
    }

    @Override
    @Transactional
    public LikeResponse unlikePost(Long postId) {
        User currentUser = userService.getCurrentUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));

        Optional<Like> existingLike = likeRepository.findByUserAndPost(currentUser, post);
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
        }

        long likeCount = likeRepository.countByPostId(postId);

        return LikeResponse.builder()
                .postId(postId)
                .likeCount(likeCount)
                .liked(false)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public LikeResponse getLikeStatus(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with ID: " + postId);
        }

        User currentUser = null;
        try {
            currentUser = userService.getCurrentUser();
        } catch (Exception e) {
            // Unauthenticated view
        }

        long likeCount = likeRepository.countByPostId(postId);
        boolean liked = false;

        if (currentUser != null) {
            Post post = postRepository.findById(postId).orElse(null);
            if (post != null) {
                liked = likeRepository.existsByUserAndPost(currentUser, post);
            }
        }

        return LikeResponse.builder()
                .postId(postId)
                .likeCount(likeCount)
                .liked(liked)
                .build();
    }
}
