#pragma once
#include <string>

class Card {
private:
    std::string cardNumber;
    std::string pin;

public:
    Card(const std::string& cardNumber, const std::string& pin);
    std::string getCardNumber();
    bool validatePin(const std::string& inputPin);
};