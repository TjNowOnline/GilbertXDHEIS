package org.example.gilbertxdheis.domain;

public class Favorite {
    private int favoriteId;
    private int userId;
    private int itemId;

    public Favorite(int favoriteId, int userId, int itemId) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.itemId = itemId;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}