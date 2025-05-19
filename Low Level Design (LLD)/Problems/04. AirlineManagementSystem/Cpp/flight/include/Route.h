#pragma once
#include <string>

class Route {
private:
    std::string source;
    std::string destination;

public:
    Route(std::string source, std::string destination);

    const std::string getSource() const;
    const std::string getDestination()const;
};