package com.viraj.ecommerce.payment;

import com.viraj.ecommerce.customer.CustomerResponse;
import com.viraj.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
