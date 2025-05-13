#pragma once

class Shape {
public:
    virtual void draw(int x, int y, int radius) = 0;
    virtual ~Shape() = default;
};
