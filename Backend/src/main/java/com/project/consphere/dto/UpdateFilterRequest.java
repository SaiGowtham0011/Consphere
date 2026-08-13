package com.project.consphere.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class UpdateFilterRequest {

    @NotBlank(message = "Filter name is required")
    private String name;

    private String description;

    @NotEmpty(message = "Hashtags list cannot be empty")
    @Size(min = 3, max = 10, message = "Filter must contain between 3 and 10 hashtags")
    private List<String> hashtags;

    public UpdateFilterRequest() {
    }

    public UpdateFilterRequest(String name, String description, List<String> hashtags) {
        this.name = name;
        this.description = description;
        this.hashtags = hashtags;
    }

    public static UpdateFilterRequestBuilder builder() {
        return new UpdateFilterRequestBuilder();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getHashtags() { return hashtags; }
    public void setHashtags(List<String> hashtags) { this.hashtags = hashtags; }

    public static class UpdateFilterRequestBuilder {
        private String name;
        private String description;
        private List<String> hashtags;

        public UpdateFilterRequestBuilder name(String name) { this.name = name; return this; }
        public UpdateFilterRequestBuilder description(String description) { this.description = description; return this; }
        public UpdateFilterRequestBuilder hashtags(List<String> hashtags) { this.hashtags = hashtags; return this; }

        public UpdateFilterRequest build() {
            return new UpdateFilterRequest(name, description, hashtags);
        }
    }
}
