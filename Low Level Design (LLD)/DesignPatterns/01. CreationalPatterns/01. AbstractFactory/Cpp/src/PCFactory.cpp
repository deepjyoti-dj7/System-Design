#include "../include/PCFactory.h"

PCFactory::PCFactory(const std::string& ram, const std::string& hdd, const std::string& cpu)
    : ram(ram), hdd(hdd), cpu(cpu) {}

Computer* PCFactory::createComputer() const {
    return new PC(ram, hdd, cpu);
}
