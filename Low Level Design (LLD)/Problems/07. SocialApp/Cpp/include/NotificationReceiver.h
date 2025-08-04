#pragma once
#include <string>

class NotificationReceiver {
public:
    virtual ~NotificationReceiver() = default;
    virtual void receiveNotification(const std::string& message) = 0;
};
