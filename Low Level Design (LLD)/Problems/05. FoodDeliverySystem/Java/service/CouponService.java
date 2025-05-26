package service;

import payment.Coupon;

import java.util.HashMap;
import java.util.Map;

public class CouponService {
    private Map<String, Coupon> coupons = new HashMap<>();

    public void addCoupon(Coupon coupon) {
        coupons.put(coupon.getCode(), coupon);
    }

    public Coupon getCouponByCode(String code) {
        return coupons.get(code);
    }
}
