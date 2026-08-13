package com.project.consphere.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hashtags")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Hashtag() {
    }

    public Hashtag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static HashtagBuilder builder() {
        return new HashtagBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class HashtagBuilder {
        private Long id;
        private String name;

        public HashtagBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public HashtagBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Hashtag build() {
            return new Hashtag(id, name);
        }
    }
}
