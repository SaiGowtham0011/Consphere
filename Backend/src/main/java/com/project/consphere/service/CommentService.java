package com.project.consphere.service;

import com.project.consphere.dto.CommentResponse;
import com.project.consphere.dto.CreateCommentRequest;
import com.project.consphere.dto.UpdateCommentRequest;

import java.util.List;

public interface CommentService {

    CommentResponse addComment(Long postId, CreateCommentRequest request);

    List<CommentResponse> getCommentsByPost(Long postId);

    CommentResponse updateComment(Long commentId, UpdateCommentRequest request);

    void deleteComment(Long commentId);
}
