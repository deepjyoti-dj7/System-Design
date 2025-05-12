#pragma once
#include <string>

class Projector {
public:
    void on();
    void setInput(const std::string& input);
    void off();
};
