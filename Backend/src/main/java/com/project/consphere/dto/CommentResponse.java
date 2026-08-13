package com.project.consphere.dto;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String username;
    private String profilePicURL;
    private Long postId;

    public CommentResponse() {
    }

    public CommentResponse(Long id, String content, LocalDateTime createdAt, String username, String profilePicURL, Long postId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.username = username;
        this.profilePicURL = profilePicURL;
        this.postId = postId;
    }

    public static CommentResponseBuilder builder() {
        return new CommentResponseBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getProfilePicURL() { return profilePicURL; }
    public void setProfilePicURL(String profilePicURL) { this.profilePicURL = profilePicURL; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public static class CommentResponseBuilder {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private String username;
        private String profilePicURL;
        private Long postId;

        public CommentResponseBuilder id(Long id) { this.id = id; return this; }
        public CommentResponseBuilder content(String content) { this.content = content; return this; }
        public CommentResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public CommentResponseBuilder username(String username) { this.username = username; return this; }
        public CommentResponseBuilder profilePicURL(String profilePicURL) { this.profilePicURL = profilePicURL; return this; }
        public CommentResponseBuilder postId(Long postId) { this.postId = postId; return this; }

        public CommentResponse build() {
            return new CommentResponse(id, content, createdAt, username, profilePicURL, postId);
        }
    }
}
