#include "../include/FoodItem.h"

FoodItem::FoodItem(const std::string& id, const std::string& name, const double& price) 
: id(id), name(name), price(price) {}

const std::string FoodItem::getId() const {
    return id;
}

const std::string FoodItem::getName() const {
    return name;
}

const std::string FoodItem::getPrice() const {
    return price;
}