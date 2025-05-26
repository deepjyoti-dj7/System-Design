package service;

import models.Order;
import utils.OrderStatus;

import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private Map<String, Order> orders = new HashMap<>();

    public void placeOrder(Order order) {
        orders.put(order.getId(), order);
    }

    public Order getOrderById(String id) {
        return orders.get(id);
    }

    public void updateOrderStatus(String id, OrderStatus status) {
        Order order = orders.get(id);
        if (order != null) {
            order.setStatus(status);
        }
    }
}
