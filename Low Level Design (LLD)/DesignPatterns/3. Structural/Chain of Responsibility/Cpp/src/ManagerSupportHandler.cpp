#include "../include/ManagerSupportHandler.h"
#include <iostream>

void ManagerSupportHandler::handleRequest(const SupportRequest& request) {
    if (request.getType() == "advanced") {
        std::cout << "ManagerSupportHandler: Handling advanced request.\n";
    } else {
        std::cout << "ManagerSupportHandler: Cannot handle request of type " << request.getType() << "\n";
    }
}
