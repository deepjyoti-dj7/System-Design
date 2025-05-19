#include "../include/User.h"

User::User(const std::string& id, const std::string& name, const std::string& email,
           const std::string& phone, UserRole role)
    : id(id), name(name), email(email), phone(phone), role(role) {}

std::string User::getId() const {
    return id;
}

std::string User::getName() const {
    return name;
}

std::string User::getEmail() const {
    return email;
}

std::string User::getPhone() const {
    return phone;
}

UserRole User::getRole() const {
    return role;
}
