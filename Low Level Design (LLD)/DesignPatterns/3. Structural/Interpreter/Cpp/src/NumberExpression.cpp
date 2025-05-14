#include "../include/NumberExpression.h"

NumberExpression::NumberExpression(int value) : number(value) {}

int NumberExpression::interpret(Context&) {
    return number;
}
