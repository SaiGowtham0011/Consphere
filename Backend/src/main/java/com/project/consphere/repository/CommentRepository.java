package com.project.consphere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.consphere.model.Comment;

import java .util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
}

