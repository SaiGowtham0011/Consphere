package com.project.consphere.dto;

public class CreatePostRequest {
    private String caption;
    private String imageUrl;

    public CreatePostRequest() {
    }

    public CreatePostRequest(String caption, String imageUrl) {
        this.caption = caption;
        this.imageUrl = imageUrl;
    }

    public static CreatePostRequestBuilder builder() {
        return new CreatePostRequestBuilder();
    }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public static class CreatePostRequestBuilder {
        private String caption;
        private String imageUrl;

        public CreatePostRequestBuilder caption(String caption) { this.caption = caption; return this; }
        public CreatePostRequestBuilder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }

        public CreatePostRequest build() {
            return new CreatePostRequest(caption, imageUrl);
        }
    }
}
