#pragma once
#include "User.h"
#include "ChatMediator.h"
#include <iostream>

class ConcreteUser : public User {
public:
    ConcreteUser(ChatMediator* mediator, const std::string& name);
    void send(const std::string& message) override;
    void receive(const std::string& message, User* sender) override;
};
