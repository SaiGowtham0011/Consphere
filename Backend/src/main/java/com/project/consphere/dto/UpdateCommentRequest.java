package com.project.consphere.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateCommentRequest {

    @NotBlank(message = "Comment content cannot be empty")
    private String content;

    public UpdateCommentRequest() {
    }

    public UpdateCommentRequest(String content) {
        this.content = content;
    }

    public static UpdateCommentRequestBuilder builder() {
        return new UpdateCommentRequestBuilder();
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public static class UpdateCommentRequestBuilder {
        private String content;

        public UpdateCommentRequestBuilder content(String content) { this.content = content; return this; }

        public UpdateCommentRequest build() {
            return new UpdateCommentRequest(content);
        }
    }
}
