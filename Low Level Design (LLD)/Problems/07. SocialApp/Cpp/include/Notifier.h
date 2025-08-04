#include "NotificationReceiver.h"

class Notifier {
public:
    void sendGreeting(NotificationReceiver& receiver) {
        receiver.receiveNotification("Hello, world!");
        receiver.receiveNotification("Hello again!");
    }
};
