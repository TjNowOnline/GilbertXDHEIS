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

    public int getItemsSold() {
        return itemsSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getRating() {
        return rating;
    }
}