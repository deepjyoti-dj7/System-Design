#include "../include/SupportHandler.h"

SupportHandler::SupportHandler() : nextHandler(nullptr) {}

SupportHandler::~SupportHandler() {}

void SupportHandler::setNextHandler(SupportHandler* handler) {
    nextHandler = handler;
}
