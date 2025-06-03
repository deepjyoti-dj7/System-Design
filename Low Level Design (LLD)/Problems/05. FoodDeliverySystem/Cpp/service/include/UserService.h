#pragma once

#include <string>
#include <unordered_map>
#include <memory>
#include "../../models/include/User.h"

class UserService {
private:
    std::unordered_map<std::string, std::shared_ptr<User>> users;

public:
    void registerUser(const std::shared_ptr<User>& user);
    const std::shared_ptr<const User> getUserById(const std::string& id) const;
};