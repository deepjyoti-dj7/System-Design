#include "../include/Order.h"

Order::Order(const std::string& id, const User& customer, const Restaurant& restaurant,
             const std::vector<FoodItem>& items, double totalAmount, OrderStatus status)
    : id(id), customer(customer), restaurant(restaurant),
      items(items), totalAmount(totalAmount), status(status) {}

const std::string& Order::getId() const {
    return id;
}

const User& Order::getCustomer() const {
    return customer;
}

const Restaurant& Order::getRestaurant() const {
    return restaurant;
}

const std::vector<FoodItem>& Order::getItems() const {
    return items;
}

double Order::getTotalAmount() const {
    return totalAmount;
}

OrderStatus Order::getStatus() const {
    return status;
}

void Order::setStatus(OrderStatus status) {
    this->status = status;
}
