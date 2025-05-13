#pragma once
#include <string>

class Internet {
public:
    virtual void connectTo(const std::string& serverHost) = 0;
    virtual ~Internet() = default;
};
