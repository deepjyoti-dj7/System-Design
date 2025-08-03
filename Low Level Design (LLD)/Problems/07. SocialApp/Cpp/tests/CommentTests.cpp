#include <gtest/gtest.h>
#include <memory>
#include "Comment.h"
#include "User.h"

TEST(CommentTest, CreationAndAccess) {
    auto user = std::make_shared<User>(1, "Tester");
    Comment comment(1, user, "This is a test comment");

    EXPECT_EQ(comment.getId(), 1);
    EXPECT_EQ(comment.getAuthor(), user);
    EXPECT_EQ(comment.getContent(), "This is a test comment");
}

