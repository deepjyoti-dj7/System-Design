#pragma once
#include "Observer.h"
#include "Subject.h"
#include <iostream>

class LaptopDisplay : public Observer {
private:
    Subject* subject;

public:
    LaptopDisplay(Subject* subject);
    void update(float temperature) override;
};
