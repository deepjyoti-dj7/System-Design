#pragma once
#include "Computer.h"
#include <memory>

class ComputerAbstractFactory {
public:
    virtual Computer* createComputer() const = 0;
    virtual ~ComputerAbstractFactory() = default;
};
