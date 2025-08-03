#pragma once
#include "User.h"
#include "Post.h"
#include "Comment.h"
#include "Story.h"
#include <unordered_map>
#include <memory>

class SocialMediaApp {
private:
    int userId_ = 0, postId_ = 0, commentId_ = 0, storyId_ = 0;
    std::unordered_map<int, std::shared_ptr<User>> users_;
    std::unordered_map<int, std::shared_ptr<Post>> posts_;
    std::unordered_map<int, std::shared_ptr<Comment>> comments_;
    std::unordered_map<int, std::shared_ptr<Story>> stories_;

public:
    std::shared_ptr<User> createUser(const std::string& name);
    std::shared_ptr<Post> createPost(std::shared_ptr<User> author, const std::string& content);
    std::shared_ptr<Comment> createComment(std::shared_ptr<User> author, const std::string& content);
    std::shared_ptr<Story> createStory(std::shared_ptr<User> author, const std::string& content);

    std::shared_ptr<User> getUser(int id);
    std::shared_ptr<Post> getPost(int id);
    std::shared_ptr<Comment> getComment(int id);
    std::shared_ptr<Story> getStory(int id);
};
