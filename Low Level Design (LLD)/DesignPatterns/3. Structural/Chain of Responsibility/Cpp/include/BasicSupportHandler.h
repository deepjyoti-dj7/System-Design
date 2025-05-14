#pragma once
#include "SupportHandler.h"

class BasicSupportHandler : public SupportHandler {
public:
    void handleRequest(const SupportRequest& request) override;
};
