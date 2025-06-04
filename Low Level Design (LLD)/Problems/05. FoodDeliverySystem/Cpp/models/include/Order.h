#pragma once

#include <string>
#include <vector>
#include "FoodItem.h"
#include "User.h"
#include "Restaurant.h"
#include "../../utils/include/OrderStatus.h"

class Order {
private:
    std::string id;
    User customer;
    Restaurant restaurant;
    std::vector<FoodItem> items;
    double totalAmount;
    OrderStatus status;

public:
    Order(const std::string& id, const User& customer, const Restaurant& restaurant,
          const std::vector<FoodItem>& items, double totalAmount, OrderStatus status);

    const std::string& getId() const;
    const User& getCustomer() const;
    const Restaurant& getRestaurant() const;
    const std::vector<FoodItem>& getItems() const;
    double getTotalAmount() const;
    OrderStatus getStatus() const;

    void setStatus(OrderStatus status);
};