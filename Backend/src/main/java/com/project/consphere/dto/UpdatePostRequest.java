package com.project.consphere.dto;

public class UpdatePostRequest {
    private String caption;
    private String imageUrl;

    public UpdatePostRequest() {
    }

    public UpdatePostRequest(String caption, String imageUrl) {
        this.caption = caption;
        this.imageUrl = imageUrl;
    }

    public static UpdatePostRequestBuilder builder() {
        return new UpdatePostRequestBuilder();
    }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public static class UpdatePostRequestBuilder {
        private String caption;
        private String imageUrl;

        public UpdatePostRequestBuilder caption(String caption) { this.caption = caption; return this; }
        public UpdatePostRequestBuilder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }

        public UpdatePostRequest build() {
            return new UpdatePostRequest(caption, imageUrl);
        }
    }
}
