#pragma once
#include "../include/CoffeeDecorator.h"

class SugarDecorator : public CoffeeDecorator {
public:
    SugarDecorator(Coffee* coffee);
    std::string getDescription() const override;
    double getCost() const override;
};
