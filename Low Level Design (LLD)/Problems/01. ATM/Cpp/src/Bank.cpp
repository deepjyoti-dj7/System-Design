#include "../include/Bank.h"

void Bank::addUser(User* user) {
    users[user->getCard()->getCardNumber()] = user;
}

User* Bank::authenticate(const std::string& cardNumber, const std::string& pin) {
    auto it = users.find(cardNumber);
    if (it != users.end() && it->second->getCard()->validatePin(pin)) {
        return it->second;
    }
    return nullptr;
}