#include "../include/NotificationService.h"

void NotificationService::sendNotification(const std::string& message, const std::string& recipient) {
    std::cout << "Notification sent to " + recipient + ": " + message << std::endl;
}