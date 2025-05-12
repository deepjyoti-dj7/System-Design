#pragma once
#include "../include/FileComponent.h"
#include <string>

class File : public FileComponent {
private:
    std::string name;

public:
    File(const std::string& name);
    void showDetails(const std::string& indent) override;
};
