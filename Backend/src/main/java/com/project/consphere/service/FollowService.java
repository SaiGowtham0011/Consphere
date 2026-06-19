package com.project.consphere.service;

import com.project.consphere.model.Follow;
import com.project.consphere.model.Post;
import com.project.consphere.model.User;

import java.util.List;

public interface FollowService {

    Follow addFollow(Follow follow);
    Follow deleteFollow(Follow follow);

    //This finds the users whom are followed by the "User follower"
    List<Follow> findByFollower(User follower);

    //This finds the users whom are following "User follower"
    List<Follow> findByFollowing(User following);

    //checks whether user is following or not to follow/unfollow
    boolean checkFollow(User following, User follower);
}
