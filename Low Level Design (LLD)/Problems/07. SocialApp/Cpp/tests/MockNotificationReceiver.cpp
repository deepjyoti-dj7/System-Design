#include <gtest/gtest.h>
#include <gmock/gmock.h>
#include "../include/Notifier.h"
#include "../include/NotificationReceiver.h"

class MockNotificationReceiver : public NotificationReceiver {
public:
    MOCK_METHOD(void, receiveNotification, (const std::string& message), (override));
};

TEST(NotificationMockTest, NotifierSendsHelloMessages) {
    MockNotificationReceiver mockReceiver;
    Notifier notifier;

    EXPECT_CALL(mockReceiver, receiveNotification(testing::HasSubstr("Hello")))
        .Times(2);

    notifier.sendGreeting(mockReceiver); // <--- Now you're testing real behavior
}

