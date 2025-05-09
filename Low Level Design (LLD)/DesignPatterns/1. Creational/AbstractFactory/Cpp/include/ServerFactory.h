#pragma once
#include "ComputerAbstractFactory.h"
#include "Server.h"
#include <string>
#include <memory>

class ServerFactory : public ComputerAbstractFactory {
private:
    std::string ram;
    std::string hdd;
    std::string cpu;

public:
    ServerFactory(const std::string& ram, const std::string& hdd, const std::string& cpu);
    Computer* createComputer() const override;
};
