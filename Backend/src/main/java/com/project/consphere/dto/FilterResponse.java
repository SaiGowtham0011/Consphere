package com.project.consphere.dto;

import java.util.List;

public class FilterResponse {
    private Long id;
    private String name;
    private String description;
    private boolean builtIn;
    private List<String> hashtags;
    private String ownerUsername;

    public FilterResponse() {
    }

    public FilterResponse(Long id, String name, String description, boolean builtIn, List<String> hashtags, String ownerUsername) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.builtIn = builtIn;
        this.hashtags = hashtags;
        this.ownerUsername = ownerUsername;
    }

    public static FilterResponseBuilder builder() {
        return new FilterResponseBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isBuiltIn() { return builtIn; }
    public void setBuiltIn(boolean builtIn) { this.builtIn = builtIn; }

    public List<String> getHashtags() { return hashtags; }
    public void setHashtags(List<String> hashtags) { this.hashtags = hashtags; }

    public String getOwnerUsername() { return ownerUsername; }
    public void setOwnerUsername(String ownerUsername) { this.ownerUsername = ownerUsername; }

    public static class FilterResponseBuilder {
        private Long id;
        private String name;
        private String description;
        private boolean builtIn;
        private List<String> hashtags;
        private String ownerUsername;

        public FilterResponseBuilder id(Long id) { this.id = id; return this; }
        public FilterResponseBuilder name(String name) { this.name = name; return this; }
        public FilterResponseBuilder description(String description) { this.description = description; return this; }
        public FilterResponseBuilder builtIn(boolean builtIn) { this.builtIn = builtIn; return this; }
        public FilterResponseBuilder hashtags(List<String> hashtags) { this.hashtags = hashtags; return this; }
        public FilterResponseBuilder ownerUsername(String ownerUsername) { this.ownerUsername = ownerUsername; return this; }

        public FilterResponse build() {
            return new FilterResponse(id, name, description, builtIn, hashtags, ownerUsername);
        }
    }
}
