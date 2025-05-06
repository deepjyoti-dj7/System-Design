#include "../include/Card.h"

Card::Card(const std::string& cardNumber, const std::string& pin) {
    this->cardNumber = cardNumber;
    this->pin = pin;
}

std::string Card::getCardNumber() {
    return cardNumber;
}

bool Card::validatePin(const std::string& inputPin) {
    return inputPin == pin;
}