#pragma once
#include "Expression.h"

class NumberExpression : public Expression {
private:
    int number;

public:
    NumberExpression(int value);
    int interpret(Context& context) override;
};
