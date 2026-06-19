package com.project.consphere.service;

import com.project.consphere.model.Comment;
import com.project.consphere.model.Post;
import com.project.consphere.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment addComment(Comment comment){
        return commentRepository.save(comment);
    }

    @Override
    public Comment deleteComment(Comment comment){
        commentRepository.delete(comment);
        return comment;
    }

    @Override
    public List<Comment> findCommentsByPost(Post post){
        return commentRepository.findAllByPost(post);
    }
}
