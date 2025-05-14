#include "../include/VariableExpression.h"

VariableExpression::VariableExpression(const std::string& varName) : name(varName) {}

int VariableExpression::interpret(Context& context) {
    return context.lookup(name);
}
