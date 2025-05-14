#include "../include/AddExpression.h"

AddExpression::AddExpression(Expression* l, Expression* r) : left(l), right(r) {}

int AddExpression::interpret(Context& context) {
    return left->interpret(context) + right->interpret(context);
}

AddExpression::~AddExpression() {
    delete left;
    delete right;
}
