#pragma once
#include <string>

class Product {
public:
    virtual Product* clone() const = 0;
    virtual void showDetails() const = 0;
    virtual ~Product() = default;
};