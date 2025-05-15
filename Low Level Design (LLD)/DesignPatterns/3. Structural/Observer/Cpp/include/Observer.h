#pragma once

class Observer {
public:
    virtual void update(float temperature) = 0;
    virtual ~Observer() = default;
};
