#include "../include/User.h"

User::User(const std::string& userId,
           const std::string& name,
           const std::string& email,
           const std::string& phoneNumber)
    : userId(userId), name(name), email(email), phoneNumber(phoneNumber) {}

const std::string& User::getUserId() const {
    return userId;
}

const std::string& User::getName() const {
    return name;
}

const std::string& User::getEmail() const {
    return email;
}

const std::string& User::getPhoneNumber() const {
    return phoneNumber;
}

const std::unordered_map<std::string, double>& User::getBalanceSheet() const {
    return balanceSheet;
}

void User::updateBalanceSheet(const std::string& otherUserId, double amount) {
    balanceSheet[otherUserId] += amount;
}

bool User::operator==(const User& other) const {
    return userId == other.userId;
}
