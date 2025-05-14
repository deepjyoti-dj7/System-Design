#pragma once
class Context;

class Expression {
public:
    virtual int interpret(Context& context) = 0;
    virtual ~Expression() = default;
};
