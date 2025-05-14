#include "./include/SupportRequest.h"
#include "./include/BasicSupportHandler.h"
#include "./include/SupervisorSupportHandler.h"
#include "./include/ManagerSupportHandler.h"

int main() {
    // Create handlers
    BasicSupportHandler basic;
    SupervisorSupportHandler supervisor;
    ManagerSupportHandler manager;

    // Set chain
    basic.setNextHandler(&supervisor);
    supervisor.setNextHandler(&manager);

    // Create and process requests
    SupportRequest r1("basic");
    SupportRequest r2("intermediate");
    SupportRequest r3("advanced");
    SupportRequest r4("unknown");

    basic.handleRequest(r1);
    basic.handleRequest(r2);
    basic.handleRequest(r3);
    basic.handleRequest(r4);

    return 0;
}
