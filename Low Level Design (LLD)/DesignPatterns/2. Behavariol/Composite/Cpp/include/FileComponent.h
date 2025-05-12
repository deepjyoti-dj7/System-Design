#pragma once
#include <string>

class FileComponent {
public:
    virtual void showDetails(const std::string& indent) = 0;
    virtual ~FileComponent() = default;
};
