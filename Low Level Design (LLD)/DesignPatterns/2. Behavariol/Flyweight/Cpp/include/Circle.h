#pragma once
#include <string>
#include "Shape.h"

class Circle : public Shape {
private:
    std::string color; // Intrinsic state
public:
    Circle(const std::string& color);
    void draw(int x, int y, int radius) override;
};
