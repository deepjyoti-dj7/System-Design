#pragma once
#include <unordered_map>
#include <string>

class Context {
private:
    std::unordered_map<std::string, int> variables;

public:
    void assign(const std::string& var, int value);
    int lookup(const std::string& var) const;
};
