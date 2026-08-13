package com.project.consphere.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponse {
    private Long id;
    private String caption;
    private String imageUrl;
    private LocalDateTime createdAt;
    private String username;
    private String profilePicURL;
    private long likeCount;
    private long commentCount;
    private boolean liked;
    private List<String> hashtags;

    public PostResponse() {
    }

    public PostResponse(Long id, String caption, String imageUrl, LocalDateTime createdAt, String username, String profilePicURL, long likeCount, long commentCount, boolean liked, List<String> hashtags) {
        this.id = id;
        this.caption = caption;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.username = username;
        this.profilePicURL = profilePicURL;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.liked = liked;
        this.hashtags = hashtags;
    }

    public static PostResponseBuilder builder() {
        return new PostResponseBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getProfilePicURL() { return profilePicURL; }
    public void setProfilePicURL(String profilePicURL) { this.profilePicURL = profilePicURL; }

    public long getLikeCount() { return likeCount; }
    public void setLikeCount(long likeCount) { this.likeCount = likeCount; }

    public long getCommentCount() { return commentCount; }
    public void setCommentCount(long commentCount) { this.commentCount = commentCount; }

    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }

    public List<String> getHashtags() { return hashtags; }
    public void setHashtags(List<String> hashtags) { this.hashtags = hashtags; }

    public static class PostResponseBuilder {
        private Long id;
        private String caption;
        private String imageUrl;
        private LocalDateTime createdAt;
        private String username;
        private String profilePicURL;
        private long likeCount;
        private long commentCount;
        private boolean liked;
        private List<String> hashtags;

        public PostResponseBuilder id(Long id) { this.id = id; return this; }
        public PostResponseBuilder caption(String caption) { this.caption = caption; return this; }
        public PostResponseBuilder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public PostResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public PostResponseBuilder username(String username) { this.username = username; return this; }
        public PostResponseBuilder profilePicURL(String profilePicURL) { this.profilePicURL = profilePicURL; return this; }
        public PostResponseBuilder likeCount(long likeCount) { this.likeCount = likeCount; return this; }
        public PostResponseBuilder commentCount(long commentCount) { this.commentCount = commentCount; return this; }
        public PostResponseBuilder liked(boolean liked) { this.liked = liked; return this; }
        public PostResponseBuilder hashtags(List<String> hashtags) { this.hashtags = hashtags; return this; }

        public PostResponse build() {
            return new PostResponse(id, caption, imageUrl, createdAt, username, profilePicURL, likeCount, commentCount, liked, hashtags);
        }
    }
}
