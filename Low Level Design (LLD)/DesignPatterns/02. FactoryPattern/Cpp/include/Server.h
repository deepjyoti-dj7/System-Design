#pragma once
#include "Computer.h"
#include <string>

class Server : public Computer {
private:
    std::string ram;
    std::string hdd;
    std::string cpu;

public:
    Server(const std::string& ram, const std::string& hdd, const std::string& cpu);

    std::string getRAM() const override;
    std::string getHDD() const override;
    std::string getCPU() const override;

};