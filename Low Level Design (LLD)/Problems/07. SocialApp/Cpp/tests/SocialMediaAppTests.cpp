#include <gtest/gtest.h>
#include "SocialMediaApp.h"

TEST(SocialMediaAppTest, BasicAppFlow) {
    SocialMediaApp app;
    auto u1 = app.createUser("Sam");
    auto u2 = app.createUser("Max");

    auto post = app.createPost(u1, "Hello World!");
    EXPECT_EQ(post->getAuthor(), u1);

    post->addLike(u2);
    EXPECT_EQ(post->getLikes().size(), 1);

    auto comm = app.createComment(u2, "Nice!");
    post->addComment(comm);
    EXPECT_EQ(post->getComments().size(), 1);

    auto story = app.createStory(u1, "My Story");
    EXPECT_FALSE(story->isExpired());
}

