package org.example.gilbertxdheis.domain;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    private int profileId;
    private String username;
    private String email;
    private String bio;
    private String profilePicture;
    private int userId;
    private String role;
    private int itemsSold;
    private int followers;
    private List<Item> soldItems = new ArrayList<>();
    private String businessId;


    // Default constructor
    public Profile() {
    }

    // Parameterized constructor
    public Profile(int profileId, String username, String email, String bio, String profilePicture, int userId, String role, int itemsSold, int followers, String businessId) {
        this.profileId = profileId;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.userId = userId;
        this.role = role;
        this.itemsSold = itemsSold;
        this.followers = followers;
        this.businessId = businessId; // Initialize new field
    }

    // Getters and Setters
    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
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

    public int getItemsSold() {
        return itemsSold;
    }

    public void setItemsSold(int itemsSold) {
        this.itemsSold = itemsSold;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public List<Item> getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(List<Item> soldItems) {
        this.soldItems = soldItems;
    }

    public String getBusinessId() { // Getter for businessId
        return businessId;
    }

    public void setBusinessId(String businessId) { // Setter for businessId
        this.businessId = businessId;
    }
}