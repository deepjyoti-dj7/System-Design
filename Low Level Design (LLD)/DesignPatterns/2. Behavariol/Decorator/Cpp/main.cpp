#include <iostream>
#include "./include/SimpleCoffee.h"
#include "./include/MilkDecorator.h"
#include "./include/SugarDecorator.h"

int main() {
    Coffee* coffee = new SimpleCoffee();
    std::cout << coffee->getDescription() << " $" << coffee->getCost() << std::endl;

    coffee = new MilkDecorator(coffee);
    std::cout << coffee->getDescription() << " $" << coffee->getCost() << std::endl;

    coffee = new SugarDecorator(coffee);
    std::cout << coffee->getDescription() << " $" << coffee->getCost() << std::endl;

    delete coffee;
    return 0;
}
