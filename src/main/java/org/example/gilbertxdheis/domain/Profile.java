package org.example.gilbertxdheis.domain;

public class Profile {
    private int profileId;
    private String bio;
    private String profilePicture;
    private int userId;

    public Profile(int profileId, String bio, String profilePicture, int userId) {
        this.profileId = profileId;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.userId = userId;
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
}