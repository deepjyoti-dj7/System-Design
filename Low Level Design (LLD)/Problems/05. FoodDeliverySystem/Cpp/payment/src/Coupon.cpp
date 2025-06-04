#include "../include/Coupon.h"

Coupon::Coupon() 
    : code(""), discountPercentage(0.0), isActive(false) {}

Coupon::Coupon(const std::string& code, double discountPercentage, bool isActive)
    : code(code), discountPercentage(discountPercentage), isActive(isActive) {}

const std::string& Coupon::getCode() const {
    return code;
}

double Coupon::getDiscountPercentage() const {
    return discountPercentage;
}

bool Coupon::getIsActive() const {
    return isActive;
}