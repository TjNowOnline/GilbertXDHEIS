package org.example.gilbertxdheis.domain;

public class User {
    private int userId;
    private String username;
    private String email;
    private String password;
    private boolean isVerified;
    private boolean isAdmin;

    public User(int userId, String username, String email, String password, boolean isVerified, boolean isAdmin) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isVerified = isVerified;
        this.isAdmin = isAdmin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return username + " (" + email + ")";
    }
}