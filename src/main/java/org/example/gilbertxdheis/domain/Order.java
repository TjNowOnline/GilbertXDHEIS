package org.example.gilbertxdheis.domain;

import java.util.Date;

public class Order {
    private int orderId;
    private int buyerId;
    private int itemId;
    private Date orderDate;
    private String status;
    private double totalPrice;

    public Order(int orderId, int buyerId, int itemId, Date orderDate, String status, double totalPrice) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.itemId = itemId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}