package com.project.consphere.dto;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String profilePicURL;

    public UserResponse() {
    }

    public UserResponse(Long id, String username, String email, String firstName, String lastName, String mobileNumber, String profilePicURL) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.profilePicURL = profilePicURL;
    }

    public static UserResponseBuilder builder() {
        return new UserResponseBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getProfilePicURL() { return profilePicURL; }
    public void setProfilePicURL(String profilePicURL) { this.profilePicURL = profilePicURL; }

    public static class UserResponseBuilder {
        private Long id;
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private String mobileNumber;
        private String profilePicURL;

        public UserResponseBuilder id(Long id) { this.id = id; return this; }
        public UserResponseBuilder username(String username) { this.username = username; return this; }
        public UserResponseBuilder email(String email) { this.email = email; return this; }
        public UserResponseBuilder firstName(String firstName) { this.firstName = firstName; return this; }
        public UserResponseBuilder lastName(String lastName) { this.lastName = lastName; return this; }
        public UserResponseBuilder mobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; return this; }
        public UserResponseBuilder profilePicURL(String profilePicURL) { this.profilePicURL = profilePicURL; return this; }

        public UserResponse build() {
            return new UserResponse(id, username, email, firstName, lastName, mobileNumber, profilePicURL);
        }
    }
}
