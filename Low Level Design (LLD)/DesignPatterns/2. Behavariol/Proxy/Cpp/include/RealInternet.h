#pragma once
#include "Internet.h"
#include <iostream>

class RealInternet : public Internet {
public:
    void connectTo(const std::string& serverHost) override;
};
