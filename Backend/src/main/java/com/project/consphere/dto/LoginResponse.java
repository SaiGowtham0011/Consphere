package com.project.consphere.dto;

public class LoginResponse {
    private String token;
    private String username;
    private Long userId;
    private String profilePicURL;

    public LoginResponse() {
    }

    public LoginResponse(String token, String username, Long userId, String profilePicURL) {
        this.token = token;
        this.username = username;
        this.userId = userId;
        this.profilePicURL = profilePicURL;
    }

    public static LoginResponseBuilder builder() {
        return new LoginResponseBuilder();
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getProfilePicURL() { return profilePicURL; }
    public void setProfilePicURL(String profilePicURL) { this.profilePicURL = profilePicURL; }

    public static class LoginResponseBuilder {
        private String token;
        private String username;
        private Long userId;
        private String profilePicURL;

        public LoginResponseBuilder token(String token) { this.token = token; return this; }
        public LoginResponseBuilder username(String username) { this.username = username; return this; }
        public LoginResponseBuilder userId(Long userId) { this.userId = userId; return this; }
        public LoginResponseBuilder profilePicURL(String profilePicURL) { this.profilePicURL = profilePicURL; return this; }

        public LoginResponse build() {
            return new LoginResponse(token, username, userId, profilePicURL);
        }
    }
}
