#pragma once

#include <iostream>
#include <string>

class NotificationService {
public:
    void sendNotification(const std::string& message, const std::string& recipient);
};