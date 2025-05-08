#pragma once
#include "ComputerAbstractFactory.h"
#include "PC.h"
#include <string>
#include <memory>

class PCFactory : public ComputerAbstractFactory {
private:
    std::string ram;
    std::string hdd;
    std::string cpu;

public:
    PCFactory(const std::string& ram, const std::string& hdd, const std::string& cpu);
    Computer* createComputer() const override;
};
