#pragma once
#include "ComputerAbstractFactory.h"

class ComputerFactory {
public:
    static Computer* getComputer(const ComputerAbstractFactory* factory);
};