#pragma once
#include "Expression.h"
#include "Context.h"

class VariableExpression : public Expression {
private:
    std::string name;

public:
    VariableExpression(const std::string& varName);
    int interpret(Context& context) override;
};
