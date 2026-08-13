package com.project.consphere.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateFilterRequest {

    @NotBlank(message = "Filter name is required")
    private String name;

    private String description;

    @NotEmpty(message = "Hashtags list cannot be empty")
    @Size(min = 3, max = 10, message = "Filter must contain between 3 and 10 hashtags")
    private List<String> hashtags;

    public CreateFilterRequest() {
    }

    public CreateFilterRequest(String name, String description, List<String> hashtags) {
        this.name = name;
        this.description = description;
        this.hashtags = hashtags;
    }

    public static CreateFilterRequestBuilder builder() {
        return new CreateFilterRequestBuilder();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getHashtags() { return hashtags; }
    public void setHashtags(List<String> hashtags) { this.hashtags = hashtags; }

    public static class CreateFilterRequestBuilder {
        private String name;
        private String description;
        private List<String> hashtags;

        public CreateFilterRequestBuilder name(String name) { this.name = name; return this; }
        public CreateFilterRequestBuilder description(String description) { this.description = description; return this; }
        public CreateFilterRequestBuilder hashtags(List<String> hashtags) { this.hashtags = hashtags; return this; }

        public CreateFilterRequest build() {
            return new CreateFilterRequest(name, description, hashtags);
        }
    }
}
