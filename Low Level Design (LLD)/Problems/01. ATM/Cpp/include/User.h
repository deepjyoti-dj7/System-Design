#pragma once
#include <string>
#include "Card.h"
#include "Account.h"

class User {
private:
    std::string name;
    Card* card;
    Account* account;

public:
    User(const std::string name, Card* card, Account* account);
    std::string getName() const;
    Card* getCard() const;
    Account* getAccount() const;
};