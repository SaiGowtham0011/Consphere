package com.project.consphere.service;

import com.project.consphere.dto.CreatePostRequest;
import com.project.consphere.dto.PostResponse;
import com.project.consphere.dto.UpdatePostRequest;
import com.project.consphere.exception.ResourceNotFoundException;
import com.project.consphere.exception.UnauthorizedException;
import com.project.consphere.model.Follow;
import com.project.consphere.model.Hashtag;
import com.project.consphere.model.Post;
import com.project.consphere.model.User;
import com.project.consphere.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    @org.springframework.context.annotation.Lazy
    private UserService userService;

    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#([A-Za-z0-9_]+)");

    @Override
    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        User currentUser = userService.getCurrentUser();

        Set<Hashtag> hashtags = extractAndProcessHashtags(request.getCaption());

        Post post = Post.builder()
                .caption(request.getCaption())
                .imageUrl(request.getImageUrl())
                .user(currentUser)
                .hashtags(hashtags)
                .build();

        Post savedPost = postRepository.save(post);
        return convertToPostResponse(savedPost, currentUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getMyPosts() {
        User currentUser = userService.getCurrentUser();
        List<Post> posts = postRepository.findAllByUserIdOrderByCreatedAtDesc(currentUser.getId());
        return posts.stream()
                .map(post -> convertToPostResponse(post, currentUser))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getUserPosts(String username) {
        User currentUser = null;
        try {
            currentUser = userService.getCurrentUser();
        } catch (Exception e) {
            // Public viewing allowed
        }
        List<Post> posts = postRepository.findAllByUserUsernameOrderByCreatedAtDesc(username);
        User finalCurrentUser = currentUser;
        return posts.stream()
                .map(post -> convertToPostResponse(post, finalCurrentUser))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostResponse updatePost(Long id, UpdatePostRequest request) {
        User currentUser = userService.getCurrentUser();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + id));

        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to update this post");
        }

        if (request.getCaption() != null) {
            post.setCaption(request.getCaption());
            Set<Hashtag> hashtags = extractAndProcessHashtags(request.getCaption());
            post.setHashtags(hashtags);
        }

        if (request.getImageUrl() != null) {
            post.setImageUrl(request.getImageUrl());
        }

        Post updatedPost = postRepository.save(post);
        return convertToPostResponse(updatedPost, currentUser);
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        User currentUser = userService.getCurrentUser();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + id));

        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to delete this post");
        }

        postRepository.delete(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getHomeFeed() {
        User currentUser = userService.getCurrentUser();

        // Get users that current user follows
        List<Follow> follows = followRepository.findByFollower(currentUser);
        List<User> followedUsers = follows.stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toList());

        List<Post> feedPosts;
        if (followedUsers.isEmpty()) {
            // If following nobody, show all posts chronologically
            feedPosts = postRepository.findAllByOrderByCreatedAtDesc();
        } else {
            // Show posts from followed users + current user
            feedPosts = postRepository.findDistinctByUserInOrUserOrderByCreatedAtDesc(followedUsers, currentUser);
        }

        return feedPosts.stream()
                .map(post -> convertToPostResponse(post, currentUser))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        User currentUser = null;
        try {
            currentUser = userService.getCurrentUser();
        } catch (Exception e) {
            // Optional
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + id));

        return convertToPostResponse(post, currentUser);
    }

    public PostResponse convertToPostResponse(Post post, User currentUser) {
        long likeCount = likeRepository.countByPostId(post.getId());
        long commentCount = commentRepository.countByPostId(post.getId());

        boolean isLiked = false;
        if (currentUser != null) {
            isLiked = likeRepository.existsByUserAndPost(currentUser, post);
        }

        List<String> hashtagNames = post.getHashtags().stream()
                .map(Hashtag::getName)
                .collect(Collectors.toList());

        return PostResponse.builder()
                .id(post.getId())
                .caption(post.getCaption())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .username(post.getUser().getUsername())
                .profilePicURL(post.getUser().getProfilePicURL())
                .likeCount(likeCount)
                .commentCount(commentCount)
                .liked(isLiked)
                .hashtags(hashtagNames)
                .build();
    }

    private Set<Hashtag> extractAndProcessHashtags(String caption) {
        Set<Hashtag> hashtags = new HashSet<>();
        if (caption == null || caption.isBlank()) {
            return hashtags;
        }

        Matcher matcher = HASHTAG_PATTERN.matcher(caption);
        Set<String> tagNames = new HashSet<>();
        while (matcher.find()) {
            tagNames.add(matcher.group(1).toLowerCase());
        }

        for (String tagName : tagNames) {
            Hashtag hashtag = hashtagRepository.findByName(tagName)
                    .orElseGet(() -> hashtagRepository.save(Hashtag.builder().name(tagName).build()));
            hashtags.add(hashtag);
        }

        return hashtags;
    }
}
