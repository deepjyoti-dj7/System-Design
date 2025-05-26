package payment;

public class Coupon {
    private String code;
    private double discountPercentage;
    private boolean isActive;

    Coupon(String code, double discountPercentage, boolean isActive) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.isActive = isActive;
    }

    public String getCode() {
        return code;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
