#include "../include/UserService.h"

std::shared_ptr<User> UserService::createUser(const std::string& userId,
                                              const std::string& name,
                                              const std::string& email,
                                              const std::string& phoneNumber) {
    auto user = std::make_shared<User>(userId, name, email, phoneNumber);
    users[userId] = user;
    return user;
}

std::shared_ptr<User> UserService::getUser(const std::string& userId) const {
    auto it = users.find(userId);
    return it != users.end() ? it->second : nullptr;
}

std::vector<std::shared_ptr<User>> UserService::getAllUsers() const {
    std::vector<std::shared_ptr<User>> allUsers;
    for (const auto& [id, user] : users) {
        allUsers.push_back(user);
    }
    return allUsers;
}
