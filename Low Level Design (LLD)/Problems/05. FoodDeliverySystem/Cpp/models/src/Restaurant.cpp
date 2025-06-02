#include "../include/Restaurant.h"

Restaurant::Restaurant(const std::string& id, const std::string& name,
                       const std::string& address, const std::vector<FoodItem>& menu)
    : id(id), name(name), address(address), menu(menu) {}

const std::string& Restaurant::getId() const {
    return id;
}

const std::string& Restaurant::getName() const {
    return name;
}

const std::string& Restaurant::getAddress() const {
    return address;
}

const std::vector<FoodItem>& Restaurant::getMenu() const {
    return menu;
}
