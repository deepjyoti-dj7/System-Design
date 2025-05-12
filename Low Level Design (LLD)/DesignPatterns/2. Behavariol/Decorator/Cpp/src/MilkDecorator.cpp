#include "../include/MilkDecorator.h"

MilkDecorator::MilkDecorator(Coffee* coffee) : CoffeeDecorator(coffee) {}

std::string MilkDecorator::getDescription() const {
    return decoratedCoffee->getDescription() + ", Milk";
}

double MilkDecorator::getCost() const {
    return decoratedCoffee->getCost() + 1.5;
}
