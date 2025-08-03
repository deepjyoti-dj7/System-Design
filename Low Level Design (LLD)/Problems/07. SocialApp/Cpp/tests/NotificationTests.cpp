#include <gtest/gtest.h>
#include <memory>
#include "User.h"
#include "Notification.h"

TEST(NotificationTest, ReceiveNotification) {
    auto u1 = std::make_shared<User>(1, "Amy");
    u1->receiveNotification("You've got mail!");
    ASSERT_FALSE(u1->getNotifications().empty());
    EXPECT_EQ(u1->getNotifications().front()->getMessage(), "You've got mail!");
}

