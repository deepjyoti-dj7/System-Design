#pragma once
#include <unordered_map>
#include <string>
#include "User.h"

class Bank {
private:    
    std::unordered_map<std::string, User*> users;
public: 
    void addUser(User* user);
    User* authenticate(const std::string& cardNumber, const std::string& pin);
};