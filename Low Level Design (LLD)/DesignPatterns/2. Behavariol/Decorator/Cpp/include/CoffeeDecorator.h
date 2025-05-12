#pragma once
#include "Coffee.h"

class CoffeeDecorator : public Coffee {
protected:
    Coffee* decoratedCoffee;
public:
    CoffeeDecorator(Coffee* coffee) : decoratedCoffee(coffee) {}
    virtual ~CoffeeDecorator() { delete decoratedCoffee; }
};
