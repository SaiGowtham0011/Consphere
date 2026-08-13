package com.project.consphere.dto;

public class FollowResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String profilePicURL;

    public FollowResponse() {
    }

    public FollowResponse(Long id, String username, String firstName, String lastName, String profilePicURL) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicURL = profilePicURL;
    }

    public static FollowResponseBuilder builder() {
        return new FollowResponseBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getProfilePicURL() { return profilePicURL; }
    public void setProfilePicURL(String profilePicURL) { this.profilePicURL = profilePicURL; }

    public static class FollowResponseBuilder {
        private Long id;
        private String username;
        private String firstName;
        private String lastName;
        private String profilePicURL;

        public FollowResponseBuilder id(Long id) { this.id = id; return this; }
        public FollowResponseBuilder username(String username) { this.username = username; return this; }
        public FollowResponseBuilder firstName(String firstName) { this.firstName = firstName; return this; }
        public FollowResponseBuilder lastName(String lastName) { this.lastName = lastName; return this; }
        public FollowResponseBuilder profilePicURL(String profilePicURL) { this.profilePicURL = profilePicURL; return this; }

        public FollowResponse build() {
            return new FollowResponse(id, username, firstName, lastName, profilePicURL);
        }
    }
}
