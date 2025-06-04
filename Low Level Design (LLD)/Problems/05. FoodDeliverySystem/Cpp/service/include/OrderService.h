#pragma once

#include <string>
#include <unordered_map>
#include <memory>
#include "../../models/include/Order.h"
#include "../../utils/include/OrderStatus.h"

class OrderService {
private:
    std::unordered_map<std::string, std::shared_ptr<Order>> orders;

public:
    void placeOrder(const std::shared_ptr<Order>& order);
    std::shared_ptr<Order> getOrderById(const std::string& id) const;
    void updateOrderStatus(const std::string& id, OrderStatus status);
};
