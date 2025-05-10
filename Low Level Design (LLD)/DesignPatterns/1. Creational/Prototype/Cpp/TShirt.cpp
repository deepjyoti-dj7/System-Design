#include <iostream>
#include "TShirt.h"

TShirt::TShirt(const std::string& size, const std::string& color)
    : size(size), color(color) {}

Product* TShirt::clone() const {
    return new TShirt(size, color); // shallow clone (primitive types)
}

void TShirt::showDetails() const {
    std::cout << "T-Shirt [Size: " << size << ", Color: " << color << "]\n";
}

void TShirt::setSize(const std::string& newSize) {
    size = newSize;
}

void TShirt::setColor(const std::string& newColor) {
    color = newColor;
}
