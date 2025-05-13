#include <iostream>
#include "../include/Circle.h"

Circle::Circle(const std::string& color) : color(color) {
    std::cout << "Creating circle of color: " << color << std::endl;
}

void Circle::draw(int x, int y, int radius) {
    std::cout << "Circle drawn [Color: " << color 
              << ", X: " << x 
              << ", Y: " << y 
              << ", Radius: " << radius 
              << "]" << std::endl << std::endl;
}
