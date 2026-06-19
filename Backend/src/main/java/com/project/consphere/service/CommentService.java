package com.project.consphere.service;

import com.project.consphere.model.Comment;
import com.project.consphere.model.Post;

import java.util.List;

public interface CommentService {
    Comment addComment(Comment comment);

    Comment deleteComment(Comment comment);

    List<Comment> findCommentsByPost(Post post);
}
