package com.project.consphere.repository;

import com.project.consphere.model.Like;
import com.project.consphere.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByPost(Post post);
    //To check whether the user liked post or not
    boolean existsByUserIdAndPostId(Long userId, Long postId);
}
