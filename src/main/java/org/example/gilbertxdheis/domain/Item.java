package org.example.gilbertxdheis.domain;

public class Item {
    private int itemId;
    private String title;
    private String description;
    private double price;
    private String condition;
    private String imageUrl;
    private String category;
    private int sellerId;
    private boolean isActive;

    public Item(int itemId, String title, String description, double price, String condition, String imageUrl, String category, int sellerId, boolean isActive) {
        this.itemId = itemId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.imageUrl = imageUrl;
        this.category = category;
        this.sellerId = sellerId;
        this.isActive = isActive;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}