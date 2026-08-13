package com.project.consphere.dto;

import java.util.List;

public class UserProfileResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String profilePicURL;
    private long followersCount;
    private long followingCount;
    private long postsCount;
    private boolean isFollowing;
    private List<PostResponse> posts;

    public UserProfileResponse() {
    }

    public UserProfileResponse(Long id, String username, String firstName, String lastName, String profilePicURL, long followersCount, long followingCount, long postsCount, boolean isFollowing, List<PostResponse> posts) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicURL = profilePicURL;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.postsCount = postsCount;
        this.isFollowing = isFollowing;
        this.posts = posts;
    }

    public static UserProfileResponseBuilder builder() {
        return new UserProfileResponseBuilder();
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

    public long getFollowersCount() { return followersCount; }
    public void setFollowersCount(long followersCount) { this.followersCount = followersCount; }

    public long getFollowingCount() { return followingCount; }
    public void setFollowingCount(long followingCount) { this.followingCount = followingCount; }

    public long getPostsCount() { return postsCount; }
    public void setPostsCount(long postsCount) { this.postsCount = postsCount; }

    public boolean isFollowing() { return isFollowing; }
    public void setFollowing(boolean following) { isFollowing = following; }

    public List<PostResponse> getPosts() { return posts; }
    public void setPosts(List<PostResponse> posts) { this.posts = posts; }

    public static class UserProfileResponseBuilder {
        private Long id;
        private String username;
        private String firstName;
        private String lastName;
        private String profilePicURL;
        private long followersCount;
        private long followingCount;
        private long postsCount;
        private boolean isFollowing;
        private List<PostResponse> posts;

        public UserProfileResponseBuilder id(Long id) { this.id = id; return this; }
        public UserProfileResponseBuilder username(String username) { this.username = username; return this; }
        public UserProfileResponseBuilder firstName(String firstName) { this.firstName = firstName; return this; }
        public UserProfileResponseBuilder lastName(String lastName) { this.lastName = lastName; return this; }
        public UserProfileResponseBuilder profilePicURL(String profilePicURL) { this.profilePicURL = profilePicURL; return this; }
        public UserProfileResponseBuilder followersCount(long followersCount) { this.followersCount = followersCount; return this; }
        public UserProfileResponseBuilder followingCount(long followingCount) { this.followingCount = followingCount; return this; }
        public UserProfileResponseBuilder postsCount(long postsCount) { this.postsCount = postsCount; return this; }
        public UserProfileResponseBuilder isFollowing(boolean isFollowing) { this.isFollowing = isFollowing; return this; }
        public UserProfileResponseBuilder posts(List<PostResponse> posts) { this.posts = posts; return this; }

        public UserProfileResponse build() {
            return new UserProfileResponse(id, username, firstName, lastName, profilePicURL, followersCount, followingCount, postsCount, isFollowing, posts);
        }
    }
}
