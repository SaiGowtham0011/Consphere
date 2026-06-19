package com.project.consphere.repository;

import com.project.consphere.model.Follow;
import com.project.consphere.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    //This finds the users whom are followed by the "User follower"
    List<Follow> findByFollower(User follower);

    //This finds the users whom are following "User follower"
    List<Follow> findByFollowing(User following);

    //check whether following or not before follow/unfollow
    boolean existsByFollowingAndFollower(User following, User follower);
}
