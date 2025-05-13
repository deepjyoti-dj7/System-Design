#pragma once
#include <unordered_map>
#include <string>
#include "Shape.h"

class ShapeFactory {
public:
    static Shape* getCircle(const std::string& color);
private:
    static std::unordered_map<std::string, Shape*> circleMap;
};
