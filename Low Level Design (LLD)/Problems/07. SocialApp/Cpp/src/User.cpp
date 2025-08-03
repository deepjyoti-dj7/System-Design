#include "../include/User.h"
#include "../include/Post.h"
#include "../include/Notification.h"
#include <algorithm>
#include <iostream>

User::User(int id, const std::string& name)
    : id_(id), name_(name) {}

void User::addFriend(std::shared_ptr<User> user) {
    friends_.insert(user);
}

void User::removeFriend(std::shared_ptr<User> user) {
    friends_.erase(user);
}

void User::sendFriendRequest(std::shared_ptr<User> to) {
    outgoingRequests_.insert(to);
    to->receiveFriendRequest(shared_from_this());
}

void User::receiveFriendRequest(std::shared_ptr<User> from) {
    incomingRequests_.insert(from);
}

void User::acceptFriendRequest(std::shared_ptr<User> from) {
    if (incomingRequests_.count(from)) {
        addFriend(from);
        from->addFriend(shared_from_this());
        incomingRequests_.erase(from);
        from->outgoingRequests_.erase(shared_from_this());
    }
}

void User::rejectFriendRequest(std::shared_ptr<User> from) {
    incomingRequests_.erase(from);
    from->outgoingRequests_.erase(shared_from_this());
}

void User::addPost(const std::shared_ptr<Post>& post) {
    posts_.push_back(post);
}

void User::receiveNotification(const std::string& msg) {
    notifications_.emplace_back(std::make_shared<Notification>(msg));
}

int User::getId() const { return id_; }
const std::string& User::getName() const { return name_; }
const std::set<std::shared_ptr<User>>& User::getFriends() const { return friends_; }
const std::vector<std::shared_ptr<Post>>& User::getPosts() const { return posts_; }
std::vector<std::shared_ptr<Notification>>& User::getNotifications() { return notifications_; }
