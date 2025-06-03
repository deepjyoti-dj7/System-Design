#pragma once

#include <string>
#include <unordered_map>
#include "../../payment/include/Coupon.h"

class CouponService {
private:
    std::unordered_map<std::string, Coupon> coupons;

public:
    void addCoupon(const Coupon& coupon);
    const Coupon* getCouponByCode(std::string& code) const;
};
