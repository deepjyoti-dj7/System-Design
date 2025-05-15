#pragma once
#include "Observer.h"
#include "Subject.h"
#include <iostream>

class PhoneDisplay : public Observer {
private:
    Subject* subject;

public:
    PhoneDisplay(Subject* subject);
    void update(float temperature) override;
};
