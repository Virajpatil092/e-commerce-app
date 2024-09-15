package com.viraj.ecommerce.product;

import java.math.BigDecimal;

public record PurchaseResponce(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
