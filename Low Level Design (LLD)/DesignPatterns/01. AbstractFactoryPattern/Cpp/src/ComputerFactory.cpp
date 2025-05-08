#include "../include/ComputerFactory.h"

Computer* ComputerFactory::getComputer(const ComputerAbstractFactory* factory) {
    return factory->createComputer();
}
