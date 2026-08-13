package com.project.consphere.service;

import com.project.consphere.dto.FollowResponse;
import com.project.consphere.exception.BadRequestException;
import com.project.consphere.exception.ResourceNotFoundException;
import com.project.consphere.model.Follow;
import com.project.consphere.model.User;
import com.project.consphere.repository.FollowRepository;
import com.project.consphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void followUser(String username) {
        User currentUser = userService.getCurrentUser();
        User targetUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        if (currentUser.getId().equals(targetUser.getId())) {
            throw new BadRequestException("You cannot follow yourself");
        }

        if (followRepository.existsByFollowerAndFollowing(currentUser, targetUser)) {
            throw new BadRequestException("You are already following " + username);
        }

        Follow follow = Follow.builder()
                .follower(currentUser)
                .following(targetUser)
                .build();

        followRepository.save(follow);
    }

    @Override
    @Transactional
    public void unfollowUser(String username) {
        User currentUser = userService.getCurrentUser();
        User targetUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        Optional<Follow> follow = followRepository.findByFollowerAndFollowing(currentUser, targetUser);
        if (follow.isPresent()) {
            followRepository.delete(follow.get());
        } else {
            throw new BadRequestException("You are not following " + username);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowResponse> getFollowers() {
        User currentUser = userService.getCurrentUser();
        List<Follow> follows = followRepository.findByFollowing(currentUser);
        return follows.stream()
                .map(follow -> convertToFollowResponse(follow.getFollower()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowResponse> getFollowing() {
        User currentUser = userService.getCurrentUser();
        List<Follow> follows = followRepository.findByFollower(currentUser);
        return follows.stream()
                .map(follow -> convertToFollowResponse(follow.getFollowing()))
                .collect(Collectors.toList());
    }

    private FollowResponse convertToFollowResponse(User user) {
        return FollowResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profilePicURL(user.getProfilePicURL())
                .build();
    }
}
