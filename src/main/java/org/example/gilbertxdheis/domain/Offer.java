package org.example.gilbertxdheis.domain;

public class Offer {
    private int offerId;
    private int userId;
    private int itemId;
    private double proposedPrice;
    private String status;

    public Offer(int offerId, int userId, int itemId, double proposedPrice, String status) {
        this.offerId = offerId;
        this.userId = userId;
        this.itemId = itemId;
        this.proposedPrice = proposedPrice;
        this.status = status;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
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

    public double getProposedPrice() {
        return proposedPrice;
    }

    public void setProposedPrice(double proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}