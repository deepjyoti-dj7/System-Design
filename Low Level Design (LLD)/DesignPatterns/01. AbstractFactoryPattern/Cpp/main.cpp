#include "./include/ComputerFactory.h"
#include "./include/PCFactory.h"
#include "./include/ServerFactory.h"
#include <iostream>

int main() {
    Computer* pc = ComputerFactory::getComputer(new PCFactory("2 GB", "500 GB", "2.4 GHz"));
    Computer* server = ComputerFactory::getComputer(new ServerFactory("10 GB", "1 TB", "2.9 GHz"));

    std::cout << "Abstract Factory PC Config:: " << *pc << std::endl;
    std::cout << "Abstract Factory Server Config:: " << *server << std::endl;

    delete pc;
    delete server;

    return 0;
}
