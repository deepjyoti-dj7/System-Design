#include <iostream>
#include "./include/Context.h"
#include "./include/VariableExpression.h"
#include "./include/NumberExpression.h"
#include "./include/AddExpression.h"

int main() {
    Context context;
    context.assign("a", 5);

    Expression* a = new VariableExpression("a");
    Expression* five = new NumberExpression(5);
    Expression* addExpr = new AddExpression(a, five);

    std::cout << "a + 5 = " << addExpr->interpret(context) << std::endl;

    delete addExpr;
    return 0;
}
