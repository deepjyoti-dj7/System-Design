#include "../include/CouponService.h"

void CouponService::addCoupon(const Coupon& coupon) {
    coupons[coupon.getCode()] = coupon;
}

const Coupon* CouponService::getCouponByCode(std::string& code) const  {
    auto it = coupons.find(code);
    if (it != coupons.end()) {
        return &it->second;
    }
    return nullptr;
}