#pragma once
#include "Card.h"
#include <string>

class CardFactory {
public:
    static Card* createCard(const std::string& number, const std::string& pin);
};
