#pragma once
#include "State.h"

class ModerationState : public State {
public:
    void edit(Document* doc) override;
    void publish(Document* doc) override;
};
