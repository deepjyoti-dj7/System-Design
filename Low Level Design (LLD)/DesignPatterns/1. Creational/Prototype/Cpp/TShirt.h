#pragma once
#include "Product.h"

class TShirt : public Product {
private:
    std::string size;
    std::string color;

public:
    TShirt(const std::string& size, const std::string& color);

    Product* clone() const override;
    void showDetails() const override;

    void setSize(const std::string& size);
    void setColor(const std::string& color);
};
