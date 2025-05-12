#pragma once
#include "../include/FileComponent.h"
#include <string>
#include <vector>

class Directory : public FileComponent {
private:
    std::string name;
    std::vector<FileComponent*> children;

public:
    Directory(const std::string& name);
    void add(FileComponent* component);
    void showDetails(const std::string& indent) override;
    ~Directory();
};
