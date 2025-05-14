#include "../include/User.h"

User::User(ChatMediator* mediator, const std::string& name)
    : mediator(mediator), name(name) {}

std::string User::getName() const {
    return name;
}
