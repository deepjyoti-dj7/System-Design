#pragma once
#include <string>
#include <memory>
#include <chrono>

class User;

class Story {
private:
    int id_;
    std::shared_ptr<User> author_;
    std::string content_;
    std::chrono::system_clock::time_point createdAt_;

public:
    Story(int id, std::shared_ptr<User> author, const std::string& content);

    int getId() const;
    std::shared_ptr<User> getAuthor() const;
    const std::string& getContent() const;
    bool isExpired() const;
};
