#include "../include/ServerFactory.h"

ServerFactory::ServerFactory(const std::string& ram, const std::string& hdd, const std::string& cpu)
    : ram(ram), hdd(hdd), cpu(cpu) {}

Computer* ServerFactory::createComputer() const {
    return new Server(ram, hdd, cpu);
}
