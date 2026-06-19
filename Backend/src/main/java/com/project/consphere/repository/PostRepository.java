package com.project.consphere.repository;

import com.project.consphere.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    //To view posts from newest to oldest order
    List<Post> findAllByOrderByCreatedAtDesc();
    //when we see someone profile
    List<Post> findAllByUserId(Long id);

}
