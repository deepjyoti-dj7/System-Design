#include <gtest/gtest.h>
#include <memory>
#include "Story.h"
#include "User.h"

TEST(StoryTest, ExpiryCheck) {
    auto u1 = std::make_shared<User>(1, "Eve");
    auto story = std::make_shared<Story>(1, u1, "My story");
    EXPECT_FALSE(story->isExpired());

    // (Optionally, simulate expiry by manipulating time dependencies or wait)
}

