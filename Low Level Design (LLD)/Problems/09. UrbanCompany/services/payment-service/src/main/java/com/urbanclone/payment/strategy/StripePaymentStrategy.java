package com.urbanclone.payment.strategy;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import com.urbanclone.payment.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class StripePaymentStrategy implements PaymentGatewayStrategy {
    
    @Value("${payment.stripe.api-key}")
    private String stripeApiKey;
    
    @Override
    public String processPayment(Payment payment) throws Exception {
        Stripe.apiKey = stripeApiKey;
        
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", payment.getAmount().multiply(new BigDecimal("100")).intValue());
        chargeParams.put("currency", "usd");
        chargeParams.put("description", "Booking Payment: " + payment.getBookingId());
        
        Charge charge = Charge.create(chargeParams);
        log.info("Stripe payment processed: {}", charge.getId());
        
        return charge.getId();
    }
    
    @Override
    public void processRefund(String transactionId, BigDecimal amount) throws Exception {
        Stripe.apiKey = stripeApiKey;
        
        Map<String, Object> refundParams = new HashMap<>();
        refundParams.put("charge", transactionId);
        refundParams.put("amount", amount.multiply(new BigDecimal("100")).intValue());
        
        Refund refund = Refund.create(refundParams);
        log.info("Stripe refund processed: {}", refund.getId());
    }
}
