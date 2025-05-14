#pragma once
#include <string>

class ChatMediator;

class User {
protected:
    ChatMediator* mediator;
    std::string name;

public:
    User(ChatMediator* mediator, const std::string& name);
    virtual void send(const std::string& message) = 0;
    virtual void receive(const std::string& message, User* sender) = 0;
    std::string getName() const;
    virtual ~User() = default;
};
