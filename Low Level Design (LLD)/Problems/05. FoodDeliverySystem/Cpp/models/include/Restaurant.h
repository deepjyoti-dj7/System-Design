#pragma once

#include <string>
#include <vector>
#include "FoodItem.h"

class Restaurant {
private:
    std::string id;
    std::string name;
    std::string address;
    std::vector<FoodItem> menu;

public:
    Restaurant(const std::string& id, const std::string& name,
               const std::string& address, const std::vector<FoodItem>& menu);

    const std::string& getId() const;
    const std::string& getName() const;
    const std::string& getAddress() const;
    const std::vector<FoodItem>& getMenu() const;
};