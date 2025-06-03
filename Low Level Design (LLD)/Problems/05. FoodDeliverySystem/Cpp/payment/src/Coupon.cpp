#include "../include/Coupon.h"

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