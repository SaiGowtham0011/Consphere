package com.project.consphere.model;

import jakarta.persistence.*;

@Entity
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "post_id"})
})
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Like() {
    }

    public Like(Long id, User user, Post post) {
        this.id = id;
        this.user = user;
        this.post = post;
    }

    public static LikeBuilder builder() {
        return new LikeBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public static class LikeBuilder {
        private Long id;
        private User user;
        private Post post;

        public LikeBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public LikeBuilder user(User user) {
            this.user = user;
            return this;
        }

        public LikeBuilder post(Post post) {
            this.post = post;
            return this;
        }

        public Like build() {
            return new Like(id, user, post);
        }
    }
}
