#include "../include/Computer.h"

std::string Computer::toString() const {
    return "RAM= " + getRAM() + ", HDD= " + getHDD() + ", CPU= " + getCPU();
}

std::ostream& operator<<(std::ostream& os, const Computer& comp) {
    os << comp.toString();
    return os;
}