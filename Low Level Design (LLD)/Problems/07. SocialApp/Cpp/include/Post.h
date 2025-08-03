#pragma once
#include <string>
#include <vector>
#include <memory>

class User;
class Comment;

class Post {
private:
    int id_;
    std::shared_ptr<User> author_;
    std::string content_;
    std::vector<std::shared_ptr<User>> likes_;
    std::vector<std::shared_ptr<Comment>> comments_;

public:
    Post(int id, std::shared_ptr<User> author, const std::string& content);

    void addLike(std::shared_ptr<User> user);
    void removeLike(std::shared_ptr<User> user);
    void addComment(const std::shared_ptr<Comment>& comment);

    int getId() const;
    std::shared_ptr<User> getAuthor() const;
    const std::string& getContent() const;
    const std::vector<std::shared_ptr<User>>& getLikes() const;
    const std::vector<std::shared_ptr<Comment>>& getComments() const;
};
