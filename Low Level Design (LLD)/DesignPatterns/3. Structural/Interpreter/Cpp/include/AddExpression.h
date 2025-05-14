#pragma once
#include "Expression.h"

class AddExpression : public Expression {
private:
    Expression* left;
    Expression* right;

public:
    AddExpression(Expression* l, Expression* r);
    int interpret(Context& context) override;
    ~AddExpression();
};
