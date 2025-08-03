#pragma once
#include <string>
#include <memory>

class User;

class Comment {
private:
    int id_;
    std::shared_ptr<User> author_;
    std::string content_;

public:
    Comment(int id, std::shared_ptr<User> author, const std::string& content);

    int getId() const;
    std::shared_ptr<User> getAuthor() const;
    const std::string& getContent() const;
};
