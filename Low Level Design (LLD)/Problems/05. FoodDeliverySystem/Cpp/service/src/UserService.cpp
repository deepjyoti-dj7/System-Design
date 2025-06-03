#include "../include/UserService.h"

void UserService::registerUser(const std::shared_ptr<User>& user) {
    users[user->getId()] = user;
}

const std::shared_ptr<const User> UserService::getUserById(const std::string& id) const {
    auto it = users.find(id);
    if (it != users.end()) {
        return it->second;
    }
    return nullptr;
}