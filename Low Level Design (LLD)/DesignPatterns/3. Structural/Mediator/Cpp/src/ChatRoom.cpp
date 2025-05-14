#include "../include/ChatRoom.h"
#include <iostream>

void ChatRoom::showMessage(const std::string& message, User* sender) {
    for (auto user : users) {
        if (user != sender) {
            user->receive(message, sender);
        }
    }
}

void ChatRoom::addUser(User* user) {
    users.push_back(user);
}
