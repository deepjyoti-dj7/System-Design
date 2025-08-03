#include "Comment.h"
#include "User.h"

Comment::Comment(int id, std::shared_ptr<User> author, const std::string& content)
    : id_(id), author_(author), content_(content) {}

int Comment::getId() const { return id_; }
std::shared_ptr<User> Comment::getAuthor() const { return author_; }
const std::string& Comment::getContent() const { return content_; }
