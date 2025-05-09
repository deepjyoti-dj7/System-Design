#pragma once
#include <string>

class Computer {
public:
    virtual std::string getRAM() const = 0;
    virtual std::string getHDD() const = 0;
    virtual std::string getCPU() const = 0;

    virtual std::string toString() const;

    virtual ~Computer() = default;
};