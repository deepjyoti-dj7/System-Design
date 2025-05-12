#include "../include/File.h"
#include <iostream>

File::File(const std::string& name) : name(name) {}

void File::showDetails(const std::string& indent) {
    std::cout << indent << "File: " << name << std::endl;
}
