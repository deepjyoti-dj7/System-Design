#include "../include/Route.h"

Route::Route(std::string source, std::string destination) 
    : source(source), destination(destination) {}

const std::string Route::getSource() const {
    return source;
}

const std::string Route::getDestination() const {
    return destination;
}