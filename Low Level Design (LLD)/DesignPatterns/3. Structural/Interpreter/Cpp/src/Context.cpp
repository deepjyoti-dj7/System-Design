#include "../include/Context.h"

void Context::assign(const std::string& var, int value) {
    variables[var] = value;
}

int Context::lookup(const std::string& var) const {
    auto it = variables.find(var);
    if (it != variables.end()) return it->second;
    return 0;
}
