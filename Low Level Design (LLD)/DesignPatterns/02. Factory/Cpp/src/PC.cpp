#include "../include/PC.h"

PC::PC(const std::string& ram, const std::string& hdd, const std::string& cpu) 
    : ram(ram), hdd(hdd), cpu(cpu) {}

std::string PC::getRAM() const {
    return ram;
}

std::string PC::getHDD() const {
    return hdd;
}

std::string PC::getCPU() const {
    return cpu;
}