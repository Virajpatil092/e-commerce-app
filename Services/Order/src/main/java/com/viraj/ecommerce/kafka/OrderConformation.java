package com.viraj.ecommerce.kafka;

import com.viraj.ecommerce.customer.CustomerResponse;
import com.viraj.ecommerce.order.PaymentMethod;
import com.viraj.ecommerce.product.PurchaseResponce;

import java.math.BigDecimal;
import java.util.List;

public record OrderConformation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse response,
        List<PurchaseResponce> purchaseResponces
) {
}
