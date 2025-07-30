#pragma once

#include "../../model/include/User.h"
#include <unordered_map>
#include <vector>
#include <memory>
#include <string>

class UserService {
private:
    std::unordered_map<std::string, std::shared_ptr<User>> users;

public:
    std::shared_ptr<User> createUser(const std::string& userId,
                                     const std::string& name,
                                     const std::string& email,
                                     const std::string& phoneNumber);

    std::shared_ptr<User> getUser(const std::string& userId) const;

    std::vector<std::shared_ptr<User>> getAllUsers() const;
};
