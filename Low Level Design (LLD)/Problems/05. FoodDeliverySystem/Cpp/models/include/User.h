#pragma once

#include <string>
#include "../../utils/OrderStatus.h"
#include "../../utils/UserRole.h"

class User {
private:
    std::string id;
    std::string name;
    std::string email;
    std::string phone;
    UserRole role;

public:
    User(const std::string& id, const std::string& name,
         const std::string& email, const std::string& phone,
         UserRole role);

    const std::string& getId() const;
    const std::string& getName() const;
    const std::string& getEmail() const;
    const std::string& getPhone() const;
    UserRole getRole() const;
};