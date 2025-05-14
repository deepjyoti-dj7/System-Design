#include "../include/ConcreteUser.h"

ConcreteUser::ConcreteUser(ChatMediator* mediator, const std::string& name)
    : User(mediator, name) {}

void ConcreteUser::send(const std::string& message) {
    std::cout << name << " sends: " << message << std::endl;
    mediator->showMessage(message, this);
}

void ConcreteUser::receive(const std::string& message, User* sender) {
    std::cout << name << " receives from " << sender->getName() << ": " << message << std::endl;
}
