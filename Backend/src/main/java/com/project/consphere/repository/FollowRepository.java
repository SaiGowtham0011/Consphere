package com.project.consphere.repository;

import com.project.consphere.model.Follow;
import com.project.consphere.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerAndFollowing(User follower, User following);

    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

    List<Follow> findByFollower(User follower);

    List<Follow> findByFollowing(User following);

    long countByFollower(User follower);

    long countByFollowing(User following);
}
