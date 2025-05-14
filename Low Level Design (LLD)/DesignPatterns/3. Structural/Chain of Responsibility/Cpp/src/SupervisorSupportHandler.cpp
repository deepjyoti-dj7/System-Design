#include "../include/SupervisorSupportHandler.h"
#include <iostream>

void SupervisorSupportHandler::handleRequest(const SupportRequest& request) {
    if (request.getType() == "intermediate") {
        std::cout << "SupervisorSupportHandler: Handling intermediate request.\n";
    } else if (nextHandler) {
        nextHandler->handleRequest(request);
    } else {
        std::cout << "SupervisorSupportHandler: No handler found for " << request.getType() << "\n";
    }
}
