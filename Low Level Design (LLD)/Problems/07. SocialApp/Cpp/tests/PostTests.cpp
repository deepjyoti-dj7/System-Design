#include <gtest/gtest.h>
#include <memory>
#include "User.h"
#include "Post.h"
#include "Comment.h"

// Test creation of post, liking and unliking
TEST(PostTest, CreatePostAndLikes) {
    auto u1 = std::make_shared<User>(1, "DJ");
    auto post = std::make_shared<Post>(1, u1, "First Post!");
    EXPECT_EQ(post->getAuthor(), u1);
    post->addLike(u1);
    EXPECT_EQ(post->getLikes().size(), 1);
    post->removeLike(u1);
    EXPECT_TRUE(post->getLikes().empty());
}

// Test adding comments to posts
TEST(PostTest, AddComments) {
    auto u1 = std::make_shared<User>(1, "A");
    auto post = std::make_shared<Post>(1, u1, "Another Post!");
    auto comm = std::make_shared<Comment>(1, u1, "Nice post!");
    post->addComment(comm);
    EXPECT_EQ(post->getComments().size(), 1);
}

