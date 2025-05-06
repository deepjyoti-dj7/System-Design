#include "../include/User.h"

User::User(const std::string name, Card* card, Account* account) 
    : name(name), card(card), account(account) {};

std::string User::getName() const {
    return name;
}

Card* User::getCard() const {
    return card;
}

Account* User::getAccount() const {
    return account;
}