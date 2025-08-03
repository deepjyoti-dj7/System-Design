#pragma once
#include <string>

class Notification {
private:
    std::string message_;

public:
    Notification(const std::string& message);

    const std::string& getMessage() const;
};
