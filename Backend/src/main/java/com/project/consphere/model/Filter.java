package com.project.consphere.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "filters")
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private boolean builtIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "filter_hashtags",
            joinColumns = @JoinColumn(name = "filter_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private Set<Hashtag> hashtags = new HashSet<>();

    public Filter() {
    }

    public Filter(Long id, String name, String description, boolean builtIn, User owner, Set<Hashtag> hashtags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.builtIn = builtIn;
        this.owner = owner;
        if (hashtags != null) {
            this.hashtags = hashtags;
        }
    }

    public static FilterBuilder builder() {
        return new FilterBuilder();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(boolean builtIn) {
        this.builtIn = builtIn;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Set<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public static class FilterBuilder {
        private Long id;
        private String name;
        private String description;
        private boolean builtIn;
        private User owner;
        private Set<Hashtag> hashtags = new HashSet<>();

        public FilterBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FilterBuilder name(String name) {
            this.name = name;
            return this;
        }

        public FilterBuilder description(String description) {
            this.description = description;
            return this;
        }

        public FilterBuilder builtIn(boolean builtIn) {
            this.builtIn = builtIn;
            return this;
        }

        public FilterBuilder owner(User owner) {
            this.owner = owner;
            return this;
        }

        public FilterBuilder hashtags(Set<Hashtag> hashtags) {
            this.hashtags = hashtags;
            return this;
        }

        public Filter build() {
            return new Filter(id, name, description, builtIn, owner, hashtags);
        }
    }
}
