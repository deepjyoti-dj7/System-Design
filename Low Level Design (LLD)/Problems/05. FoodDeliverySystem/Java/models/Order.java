package models;

import utils.OrderStatus;

import java.util.List;

public class Order {
    private String id;
    private User customer;
    private Restaurant restaurant;
    private List<FoodItem> items;
    private double totalAmount;
    private OrderStatus status;

    public Order(String id, User customer, Restaurant restaurant, List<FoodItem> items, double totalAmount, OrderStatus status) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public User getCustomer() {
        return customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public List<FoodItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
