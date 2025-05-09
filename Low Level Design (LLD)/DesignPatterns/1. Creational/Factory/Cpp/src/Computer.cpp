#include "../include/Computer.h"

std::string Computer::toString() const {
    return "RAM= " + getRAM() + ", HDD= " + getHDD() + ", CPU= " + getCPU();
}
