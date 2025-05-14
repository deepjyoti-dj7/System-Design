#include "../include/BasicSupportHandler.h"
#include <iostream>

void BasicSupportHandler::handleRequest(const SupportRequest& request) {
    if (request.getType() == "basic") {
        std::cout << "BasicSupportHandler: Handling basic request.\n";
    } else if (nextHandler) {
        nextHandler->handleRequest(request);
    } else {
        std::cout << "BasicSupportHandler: No handler found for " << request.getType() << "\n";
    }
}
