#include <iostream>
#include <memory>
#include "include/ComputerFactory.h"
#include "include/Computer.h"

void testFactoryPattern() {
    std::shared_ptr<Computer> pc = ComputerFactory::getComputer("PC", "4 GB", "1 TB", "2.5 GHz");
    std::shared_ptr<Computer> server = ComputerFactory::getComputer("SERVER", "16 GB", "2 TB", "3.0 GHz");

    std::cout << "Factory Pattern PC Config::" << pc->toString() << std::endl;
    std::cout << "Factory Pattern Server Config::" << server->toString() << std::endl;
}

int main() {
    testFactoryPattern();
    return 0;
}
