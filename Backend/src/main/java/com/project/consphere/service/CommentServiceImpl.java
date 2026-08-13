package com.project.consphere.service;

import com.project.consphere.dto.CommentResponse;
import com.project.consphere.dto.CreateCommentRequest;
import com.project.consphere.dto.UpdateCommentRequest;
import com.project.consphere.exception.ResourceNotFoundException;
import com.project.consphere.exception.UnauthorizedException;
import com.project.consphere.model.Comment;
import com.project.consphere.model.Post;
import com.project.consphere.model.User;
import com.project.consphere.repository.CommentRepository;
import com.project.consphere.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public CommentResponse addComment(Long postId, CreateCommentRequest request) {
        User currentUser = userService.getCurrentUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));

        Comment comment = Comment.builder()
                .content(request.getContent().trim())
                .user(currentUser)
                .post(post)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return convertToCommentResponse(savedComment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with ID: " + postId);
        }

        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
        return comments.stream()
                .map(this::convertToCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentResponse updateComment(Long commentId, UpdateCommentRequest request) {
        User currentUser = userService.getCurrentUser();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + commentId));

        if (!comment.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to update this comment");
        }

        comment.setContent(request.getContent().trim());
        Comment updatedComment = commentRepository.save(comment);
        return convertToCommentResponse(updatedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        User currentUser = userService.getCurrentUser();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + commentId));

        if (!comment.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not authorized to delete this comment");
        }

        commentRepository.delete(comment);
    }

    private CommentResponse convertToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .username(comment.getUser().getUsername())
                .profilePicURL(comment.getUser().getProfilePicURL())
                .postId(comment.getPost().getId())
                .build();
    }
}
