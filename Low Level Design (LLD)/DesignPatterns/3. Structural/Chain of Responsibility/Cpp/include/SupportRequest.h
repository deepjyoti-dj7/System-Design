#pragma once
#include <string>

class SupportRequest {
private:
    std::string type;

public:
    SupportRequest(const std::string& type);
    std::string getType() const;
};
