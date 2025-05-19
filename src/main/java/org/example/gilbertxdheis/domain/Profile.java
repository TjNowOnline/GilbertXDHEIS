package org.example.gilbertxdheis.domain;

public class Profile {
    private int profileId;
    private String username;
    private String email;
    private String bio;
    private String profilePicture;
    private int userId;
    private String role;

    public Profile(int profileId, String bio, String profilePicture, int userId, String role) {
        this.profileId = profileId;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.userId = userId;
        this.role = role;
    }

    public Profile() {
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}