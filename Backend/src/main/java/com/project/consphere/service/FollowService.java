package com.project.consphere.service;

import com.project.consphere.dto.FollowResponse;

import java.util.List;

public interface FollowService {

    void followUser(String username);

    void unfollowUser(String username);

    List<FollowResponse> getFollowers();

    List<FollowResponse> getFollowing();
}
