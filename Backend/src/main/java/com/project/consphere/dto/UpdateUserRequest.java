package com.project.consphere.dto;

public class UpdateUserRequest {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String profilePicURL;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String username, String email, String firstName, String lastName, String mobileNumber, String profilePicURL) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.profilePicURL = profilePicURL;
    }

    public static UpdateUserRequestBuilder builder() {
        return new UpdateUserRequestBuilder();
    }

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

    public static class UpdateUserRequestBuilder {
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private String mobileNumber;
        private String profilePicURL;

        public UpdateUserRequestBuilder username(String username) { this.username = username; return this; }
        public UpdateUserRequestBuilder email(String email) { this.email = email; return this; }
        public UpdateUserRequestBuilder firstName(String firstName) { this.firstName = firstName; return this; }
        public UpdateUserRequestBuilder lastName(String lastName) { this.lastName = lastName; return this; }
        public UpdateUserRequestBuilder mobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; return this; }
        public UpdateUserRequestBuilder profilePicURL(String profilePicURL) { this.profilePicURL = profilePicURL; return this; }

        public UpdateUserRequest build() {
            return new UpdateUserRequest(username, email, firstName, lastName, mobileNumber, profilePicURL);
        }
    }
}
