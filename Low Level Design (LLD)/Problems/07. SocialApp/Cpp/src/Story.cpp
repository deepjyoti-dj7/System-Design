#include "Story.h"
#include <chrono>

Story::Story(int id, std::shared_ptr<User> author, const std::string& content)
    : id_(id), author_(author), content_(content),
      createdAt_(std::chrono::system_clock::now()) {}

int Story::getId() const { return id_; }
std::shared_ptr<User> Story::getAuthor() const { return author_; }
const std::string& Story::getContent() const { return content_; }

bool Story::isExpired() const {
    auto now = std::chrono::system_clock::now();
    return now - createdAt_ > std::chrono::hours(24);
}
