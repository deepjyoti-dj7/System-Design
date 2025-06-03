#pragma once

#include <string>

class Coupon {
private:
    std::string code;
    double discountPercentage;
    bool isActive;

public:
    Coupon(const std::string& code, double discountPercentage, bool isActive);

    const std::string& getCode() const;
    double getDiscountPercentage() const;
    bool getIsActive() const;
};