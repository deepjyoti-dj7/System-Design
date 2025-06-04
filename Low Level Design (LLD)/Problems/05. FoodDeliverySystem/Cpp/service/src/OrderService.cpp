#include "../include/OrderService.h"

void OrderService::placeOrder(const std::shared_ptr<Order>& order) {
    orders[order->getId()] = order;
}

std::shared_ptr<Order> OrderService::getOrderById(const std::string& id) const {
    auto it = orders.find(id);
    if (it != orders.end()) {
        return it->second;
    }
    return nullptr;
}

void OrderService::updateOrderStatus(const std::string& id, OrderStatus status) {
    auto it = orders.find(id);
    if (it != orders.end() && it->second != nullptr) {
        it->second->setStatus(status);
    }
}
