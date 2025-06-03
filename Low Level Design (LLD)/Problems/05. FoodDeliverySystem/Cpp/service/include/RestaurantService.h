#pragma once

#include <string>
#include <unordered_map>
#include <memory>
#include "../../models/include/Restaurant.h"

class RestaurantService {
private:
    std::unordered_map<std::string, std::shared_ptr<Restaurant>> restaurants;

public:
    void addRestaurant(const std::shared_ptr<Restaurant>& user);
    const std::shared_ptr<const Restaurant> getRestaurantById(const std::string& id) const;
};