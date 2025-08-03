#include "Notification.h"

Notification::Notification(const std::string& message) : message_(message) {}

const std::string& Notification::getMessage() const { return message_; }
