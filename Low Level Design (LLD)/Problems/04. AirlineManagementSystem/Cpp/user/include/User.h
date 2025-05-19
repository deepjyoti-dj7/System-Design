#pragma once
#include <string>
#include "../../utils/Enums.cpp"

class User {
protected:
    std::string id;
    std::string name;
    std::string email;
    std::string phone;
    UserRole role;

public:
    User(const std::string& id, const std::string& name, const std::string& email,
         const std::string& phone, UserRole role);
    virtual ~User() = default;

    std::string getId() const;
    std::string getName() const;
    std::string getEmail() const;
    std::string getPhone() const;
    UserRole getRole() const;
};
