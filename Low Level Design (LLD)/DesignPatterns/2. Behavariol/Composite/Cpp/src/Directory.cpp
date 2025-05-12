#include "../include/Directory.h"
#include <iostream>

Directory::Directory(const std::string& name) : name(name) {}

void Directory::add(FileComponent* component) {
    children.push_back(component);
}

void Directory::showDetails(const std::string& indent) {
    std::cout << indent << "Directory: " << name << std::endl;
    for (auto child : children) {
        child->showDetails(indent + "  ");
    }
}

Directory::~Directory() {
    for (auto child : children) {
        delete child;
    }
}
