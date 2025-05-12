#pragma once
#include "CoffeeDecorator.h"

class MilkDecorator : public CoffeeDecorator {
public:
    MilkDecorator(Coffee* coffee);
    std::string getDescription() const override;
    double getCost() const override;
};
