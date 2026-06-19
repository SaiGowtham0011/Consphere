package com.project.consphere.repository;

import com.project.consphere.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.consphere.model.Comment;

import java .util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}

