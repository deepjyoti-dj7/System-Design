#pragma once
#include "Coffee.h"

class SimpleCoffee : public Coffee {
public:
    std::string getDescription() const override;
    double getCost() const override;
};
