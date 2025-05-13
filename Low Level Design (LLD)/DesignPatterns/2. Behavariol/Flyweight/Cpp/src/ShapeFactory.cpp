#include "../include/ShapeFactory.h"
#include "../include/Circle.h"

std::unordered_map<std::string, Shape*> ShapeFactory::circleMap;

Shape* ShapeFactory::getCircle(const std::string& color) {
    if (circleMap.find(color) == circleMap.end()) {
        circleMap[color] = new Circle(color);
    }
    return circleMap[color];
}
