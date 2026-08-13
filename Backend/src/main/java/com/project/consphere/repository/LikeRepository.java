package com.project.consphere.repository;

import com.project.consphere.model.Like;
import com.project.consphere.model.Post;
import com.project.consphere.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndPost(User user, Post post);

    Optional<Like> findByUserAndPost(User user, Post post);

    long countByPostId(Long postId);

    List<Like> findByPostId(Long postId);
}
