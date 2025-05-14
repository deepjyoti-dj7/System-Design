#pragma once
#include "SupportRequest.h"

class SupportHandler {
protected:
    SupportHandler* nextHandler;

public:
    SupportHandler();
    virtual ~SupportHandler();
    void setNextHandler(SupportHandler* handler);
    virtual void handleRequest(const SupportRequest& request) = 0;
};
