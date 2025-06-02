#include "../include/User.h"

User::User(const std::string& id, const std::string& name,
           const std::string& email, const std::string& phone,
           UserRole role)
    : id(id), name(name), email(email), phone(phone), role(role) {}

const std::string& User::getId() const {
    return id;
}

const std::string& User::getName() const {
    return name;
}

const std::string& User::getEmail() const {
    return email;
}

const std::string& User::getPhone() const {
    return phone;
}

UserRole User::getRole() const {
    return role;
}
