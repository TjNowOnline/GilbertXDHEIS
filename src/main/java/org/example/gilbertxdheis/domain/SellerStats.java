package org.example.gilbertxdheis.domain;

public class SellerStats {
    private int itemsSold;
    private double totalRevenue;
    private double rating;

    public SellerStats(int itemsSold, double totalRevenue, double rating) {
        this.itemsSold = itemsSold;
        this.totalRevenue = totalRevenue;
        this.rating = rating;
    }

    public SellerStats() {
    }

    public SellerStats(int i) {
    }

    public int getItemsSold() {
        return itemsSold;
    }

    public void setItemsSold(int itemsSold) {
        this.itemsSold = itemsSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}