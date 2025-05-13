#include <iostream>
#include <cstdlib>
#include <ctime>
#include "./include/ShapeFactory.h"

std::string getRandomColor() {
    std::string colors[] = {"Red", "Green", "Blue", "White", "Black"};
    return colors[std::rand() % 5];
}

int getRandomX() {
    return std::rand() % 100;
}

int getRandomY() {
    return std::rand() % 100;
}

int main() {
    std::srand(std::time(nullptr));

    for (int i = 0; i < 5; ++i) {
        std::string color = getRandomColor();
        Shape* circle = ShapeFactory::getCircle(color);
        circle->draw(getRandomX(), getRandomY(), 100);
    }

    return 0;
}
