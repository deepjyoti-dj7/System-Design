#include "../include/SugarDecorator.h"

SugarDecorator::SugarDecorator(Coffee* coffee) : CoffeeDecorator(coffee) {}

std::string SugarDecorator::getDescription() const {
    return decoratedCoffee->getDescription() + ", Sugar";
}

double SugarDecorator::getCost() const {
    return decoratedCoffee->getCost() + 0.5;
}
