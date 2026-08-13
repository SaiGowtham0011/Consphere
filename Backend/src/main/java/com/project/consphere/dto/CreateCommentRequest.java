package com.project.consphere.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateCommentRequest {

    @NotBlank(message = "Comment content cannot be empty")
    private String content;

    public CreateCommentRequest() {
    }

    public CreateCommentRequest(String content) {
        this.content = content;
    }

    public static CreateCommentRequestBuilder builder() {
        return new CreateCommentRequestBuilder();
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public static class CreateCommentRequestBuilder {
        private String content;

        public CreateCommentRequestBuilder content(String content) { this.content = content; return this; }

        public CreateCommentRequest build() {
            return new CreateCommentRequest(content);
        }
    }
}
