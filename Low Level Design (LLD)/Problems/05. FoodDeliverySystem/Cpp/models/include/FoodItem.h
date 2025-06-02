#pragma once

#include <string>

class FoodItem {
private:
    std::string id;
    std::string name;
    double price;

public:
    FoodItem(const std::string& id, const std::string& name, const double& price);

    const std::string getId() const;
    const std::string getName() const;
    const std::string getPrice() const;
};