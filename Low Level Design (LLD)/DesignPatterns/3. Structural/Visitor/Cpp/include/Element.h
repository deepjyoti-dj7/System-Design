#pragma once

class Visitor;

class Element {
public:
    virtual void accept(Visitor* visitor) = 0;
    virtual ~Element() = default;
};
