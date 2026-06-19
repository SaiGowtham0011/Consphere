package com.project.consphere.service;

import com.project.consphere.model.Follow;
import com.project.consphere.model.User;
import com.project.consphere.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Override
    public Follow addFollow(Follow follow) {
        return followRepository.save(follow);
    }

    @Override
    public Follow deleteFollow(Follow follow) {
        followRepository.delete(follow);
        return follow;
    }

    @Override
    public List<Follow> findByFollower(User follower){
        return followRepository.findByFollower(follower);
    }

    @Override
    public List<Follow> findByFollowing(User following){
        return followRepository.findByFollowing(following);
    }

    @Override
    public boolean checkFollow(User following, User follower){
        return followRepository.existsByFollowingAndFollower(following,follower);
    }
}
