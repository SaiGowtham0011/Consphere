package com.project.consphere.dto;

public class LikeResponse {
    private Long postId;
    private long likeCount;
    private boolean liked;

    public LikeResponse() {
    }

    public LikeResponse(Long postId, long likeCount, boolean liked) {
        this.postId = postId;
        this.likeCount = likeCount;
        this.liked = liked;
    }

    public static LikeResponseBuilder builder() {
        return new LikeResponseBuilder();
    }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public long getLikeCount() { return likeCount; }
    public void setLikeCount(long likeCount) { this.likeCount = likeCount; }

    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }

    public static class LikeResponseBuilder {
        private Long postId;
        private long likeCount;
        private boolean liked;

        public LikeResponseBuilder postId(Long postId) { this.postId = postId; return this; }
        public LikeResponseBuilder likeCount(long likeCount) { this.likeCount = likeCount; return this; }
        public LikeResponseBuilder liked(boolean liked) { this.liked = liked; return this; }

        public LikeResponse build() {
            return new LikeResponse(postId, likeCount, liked);
        }
    }
}
