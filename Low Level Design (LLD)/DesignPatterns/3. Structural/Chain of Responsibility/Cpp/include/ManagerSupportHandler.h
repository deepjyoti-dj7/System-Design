#pragma once
#include "SupportHandler.h"

class ManagerSupportHandler : public SupportHandler {
public:
    void handleRequest(const SupportRequest& request) override;
};
