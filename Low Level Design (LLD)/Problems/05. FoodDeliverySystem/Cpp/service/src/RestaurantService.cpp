#include "../include/RestaurantService.h"

void RestaurantService::addRestaurant(const std::shared_ptr<Restaurant>& restaurant) {
    restaurants[restaurant->getId()] = restaurant;
}

const std::shared_ptr<const Restaurant> RestaurantService::getRestaurantById(const std::string& id) const {
    auto it = restaurants.find(id);
    if (it != restaurants.end()) {
        return it->second;
    }
    return nullptr;
}