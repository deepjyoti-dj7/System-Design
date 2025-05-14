#pragma once
#include "SupportHandler.h"

class SupervisorSupportHandler : public SupportHandler {
public:
    void handleRequest(const SupportRequest& request) override;
};
