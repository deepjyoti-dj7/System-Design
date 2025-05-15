#pragma once
#include "State.h"

class PublishedState : public State {
public:
    void edit(Document* doc) override;
    void publish(Document* doc) override;
};
