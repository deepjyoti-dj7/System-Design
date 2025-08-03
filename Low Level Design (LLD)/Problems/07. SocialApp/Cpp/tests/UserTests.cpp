#include <gtest/gtest.h>
#include <memory>
#include <set>
#include "User.h"

// Test adding and removing friends
TEST(UserTest, AddRemoveFriend) {
    auto u1 = std::make_shared<User>(1, "Alice");
    auto u2 = std::make_shared<User>(2, "Bob");
    u1->addFriend(u2);
    EXPECT_TRUE(u1->getFriends().count(u2));
    u1->removeFriend(u2);
    EXPECT_FALSE(u1->getFriends().count(u2));
}

// Test sending and accepting friend requests
TEST(UserTest, FriendRequests) {
    auto u1 = std::make_shared<User>(1, "Tom");
    auto u2 = std::make_shared<User>(2, "Jerry");
    u1->sendFriendRequest(u2);
    u2->acceptFriendRequest(u1);
    EXPECT_TRUE(u1->getFriends().count(u2));
}
