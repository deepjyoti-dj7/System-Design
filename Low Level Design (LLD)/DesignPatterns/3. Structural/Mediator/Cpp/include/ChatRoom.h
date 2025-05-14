#pragma once
#include "ChatMediator.h"
#include "User.h"
#include <vector>

class ChatRoom : public ChatMediator {
private:
    std::vector<User*> users;

public:
    void showMessage(const std::string& message, User* sender) override;
    void addUser(User* user) override;
};
