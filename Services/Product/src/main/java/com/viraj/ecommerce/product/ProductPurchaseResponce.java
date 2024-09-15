package com.viraj.ecommerce.product;

import java.math.BigDecimal;

public record ProductPurchaseResponce(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        Double quantity
) {
}
