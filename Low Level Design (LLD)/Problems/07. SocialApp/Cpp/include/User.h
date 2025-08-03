#pragma once
#include <string>
#include <vector>
#include <set>
#include <memory>

class Post;
class Notification;

class User : public std::enable_shared_from_this<User> {
private:
    int id_;
    std::string name_;
    std::set<std::shared_ptr<User>> friends_;
    std::set<std::shared_ptr<User>> incomingRequests_;
    std::set<std::shared_ptr<User>> outgoingRequests_;
    std::vector<std::shared_ptr<Post>> posts_;
    std::vector<std::shared_ptr<Notification>> notifications_;
    
public:
    User(int id, const std::string& name);

    void addFriend(std::shared_ptr<User> user);
    void removeFriend(std::shared_ptr<User> user);
    void sendFriendRequest(std::shared_ptr<User> to);
    void receiveFriendRequest(std::shared_ptr<User> from);
    void acceptFriendRequest(std::shared_ptr<User> from);
    void rejectFriendRequest(std::shared_ptr<User> from);

    void addPost(const std::shared_ptr<Post>& post);
    void receiveNotification(const std::string& msg);

    int getId() const;
    const std::string& getName() const;
    const std::set<std::shared_ptr<User>>& getFriends() const;
    const std::vector<std::shared_ptr<Post>>& getPosts() const;

    std::vector<std::shared_ptr<Notification>>& getNotifications();
};
