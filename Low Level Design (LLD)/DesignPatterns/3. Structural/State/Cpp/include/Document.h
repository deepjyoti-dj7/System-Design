#pragma once
#include "State.h"

class Document {
private:
    State* state;
public:
    Document();
    ~Document();

    void setState(State* newState);
    void edit();
    void publish();
};
