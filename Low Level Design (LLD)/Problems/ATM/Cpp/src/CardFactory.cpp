#include "../include/CardFactory.h"

Card* CardFactory::createCard(const std::string& number, const std::string& pin) {
    return new Card(number, pin);
}
