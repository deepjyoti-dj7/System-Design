#pragma once
#include <string>

class User;

class ChatMediator {
public:
    virtual void showMessage(const std::string& message, User* sender) = 0;
    virtual void addUser(User* user) = 0;
    virtual ~ChatMediator() = default;
};
