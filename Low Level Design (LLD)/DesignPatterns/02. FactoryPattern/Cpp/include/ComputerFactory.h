#pragma once
#include <string>
#include <memory>
#include "Computer.h"

class ComputerFactory {
public:
    static std::shared_ptr<Computer> getComputer(const std::string& type, 
                                                 const std::string& ram,
                                                 const std::string& hdd,
                                                 const std::string& cpu);
};
