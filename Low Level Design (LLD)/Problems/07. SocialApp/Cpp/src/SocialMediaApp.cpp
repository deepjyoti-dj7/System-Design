#include "SocialMediaApp.h"

std::shared_ptr<User> SocialMediaApp::createUser(const std::string& name) {
    auto user = std::make_shared<User>(userId_++, name);
    users_[user->getId()] = user;
    return user;
}

std::shared_ptr<Post> SocialMediaApp::createPost(std::shared_ptr<User> author, const std::string& content) {
    auto post = std::make_shared<Post>(postId_++, author, content);
    posts_[post->getId()] = post;
    author->addPost(post);
    return post;
}

std::shared_ptr<Comment> SocialMediaApp::createComment(std::shared_ptr<User> author, const std::string& content) {
    auto comment = std::make_shared<Comment>(commentId_++, author, content);
    comments_[comment->getId()] = comment;
    return comment;
}

std::shared_ptr<Story> SocialMediaApp::createStory(std::shared_ptr<User> author, const std::string& content) {
    auto story = std::make_shared<Story>(storyId_++, author, content);
    stories_[story->getId()] = story;
    return story;
}

std::shared_ptr<User> SocialMediaApp::getUser(int id) {
    return users_.find(id) != users_.end() ? users_[id] : nullptr;
}
std::shared_ptr<Post> SocialMediaApp::getPost(int id) {
    return posts_.find(id) != posts_.end() ? posts_[id] : nullptr;
}
std::shared_ptr<Comment> SocialMediaApp::getComment(int id) {
    return comments_.find(id) != comments_.end() ? comments_[id] : nullptr;
}
std::shared_ptr<Story> SocialMediaApp::getStory(int id) {
    return stories_.find(id) != stories_.end() ? stories_[id] : nullptr;
}
