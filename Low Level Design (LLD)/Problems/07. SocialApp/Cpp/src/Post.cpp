#include "Post.h"
#include "User.h"
#include "Comment.h"
#include <algorithm>

Post::Post(int id, std::shared_ptr<User> author, const std::string& content)
    : id_(id), author_(author), content_(content) {}

void Post::addLike(std::shared_ptr<User> user) {
    if (std::find(likes_.begin(), likes_.end(), user) == likes_.end())
        likes_.push_back(user);
}

void Post::removeLike(std::shared_ptr<User> user) {
    likes_.erase(std::remove(likes_.begin(), likes_.end(), user), likes_.end());
}

void Post::addComment(const std::shared_ptr<Comment>& comment) {
    comments_.push_back(comment);
}

int Post::getId() const { return id_; }
std::shared_ptr<User> Post::getAuthor() const { return author_; }
const std::string& Post::getContent() const { return content_; }
const std::vector<std::shared_ptr<User>>& Post::getLikes() const { return likes_; }
const std::vector<std::shared_ptr<Comment>>& Post::getComments() const { return comments_; }
